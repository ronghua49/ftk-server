package com.risepu.ftk.web.m.controller;

import com.risepu.ftk.server.domain.AdminUser;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.service.AdminService;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.utils.ConfigUtil;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.Constant;
import com.risepu.ftk.web.api.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController

public class ManagerController implements ManagerControllerApi {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String SALT = ConfigUtil.getValue("salt");

	@Autowired
	private  OrganizationService organizationService;

	@Autowired
	private AdminService adminService;


	/**
	 * 管理员登录
	 * @param adminName
	 * @param password
	 * @param request
	 * @return
	 */
	@Override
	public ResponseEntity<Response<String>> orgLogin( String adminName,
													  @RequestParam String password, HttpServletRequest request)   {

		password = DigestUtils.md5Hex(password+SALT);

		AdminUser admin = adminService.findAdminByName(adminName);
		if(admin!=null &&admin.getPassword().equals(password)) {

			request.getSession().setAttribute(Constant.getSessionCurrUser(), admin);
			logger.debug("管理员--{},登录成功！", admin.getId());
			return ResponseEntity.ok(Response.succeed("登录成功"));

		}else {
			return ResponseEntity.ok(Response.failed(5, "用户名或者密码错误"));
		}
	}

	/**
	 * 修改密码
	 * @param password 原始密码
	 * @param newPwd 新密码
	 * @param request
	 * @return
	 */
	@Override
	public ResponseEntity<Response<String>> changePwd( String password,
													   String newPwd, HttpServletRequest request)   {

		password = DigestUtils.md5Hex(password+SALT);
		newPwd = DigestUtils.md5Hex(password+SALT);
		AdminUser admin = getCurrAdmin(request);

		if(password.equals(admin.getPassword())) {

			admin.setPassword(newPwd);
			adminService.updateAdminUser(admin);
			return ResponseEntity.ok(Response.succeed("密码修改成功"));
		}else {
			return ResponseEntity.ok(Response.failed(7, "修改失败，输入密码和服务端密码不一致"));
		}


	}

	/**
	 * 根据参数查询认证的企业信息
	 * @param key 关键字
	 * @param pageNo
	 * @param pageSize
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param state 审核状态
	 * @param request
	 * @return
	 */
	@Override
	public ResponseEntity<Response<PageResult<Organization>>> queryOrganization(@RequestParam(required=false) String key,
																				@PathVariable Integer pageNo,
																				@RequestParam Integer pageSize,
																				@RequestParam(required=false) String startTime,
																				@RequestParam(required=false) String endTime,
																				@RequestParam(required=false) Integer state,
																				HttpServletRequest request)   {
		Map<String, Object> map = new HashMap<>();

		map.put("key", key);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);





		PageResult<Organization> pageResult = organizationService.findByParam(map, pageNo, pageSize);

		return ResponseEntity.ok(Response.succeed(pageResult));

	}

	/**
	 * 保存修改后的企业信息
	 * @param organization 审核后的企业信息
	 * @return
	 */
	@Override
	public ResponseEntity<Response<String>> checkOrgInfo(@RequestBody Organization organization){

		Organization org = organizationService.findAuthenOrgById(organization.getId());

		org.setInsuranceNum(organization.getInsuranceNum());
		org.setOrgType(organization.getOrgType());
		org.setRegistedCapital(organization.getRegistedCapital());
		org.setRegistedDate(organization.getRegistedDate());
		org.setRemark(organization.getRemark());
		org.setScope(organization.getScope());
		org.setSignSts(organization.getSignSts());
		org.setStaffSize(organization.getStaffSize());
		org.setState(organization.getState());
		org.setWebsite(organization.getWebsite());

		organizationService.saveOrUpdateOrgInfo(org);

		return ResponseEntity.ok(Response.succeed("提交成功"));

	}


	@Override
	public ResponseEntity<Response<Organization>> queryOrgById(@PathVariable String orgId ){

		Organization organization = organizationService.findAuthenOrgById(orgId);
		return ResponseEntity.ok(Response.succeed(organization));
	}


	private AdminUser getCurrAdmin(HttpServletRequest request) {
		return (AdminUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
	}


	/**
	 * 退出登录
	 */
	@Override
	public ResponseEntity<Response<String>> loginOut(HttpServletRequest request){

		request.getSession().setAttribute(Constant.getSessionCurrUser(),null);
		return ResponseEntity.ok(Response.succeed("退出登录成功"));
	}


}
