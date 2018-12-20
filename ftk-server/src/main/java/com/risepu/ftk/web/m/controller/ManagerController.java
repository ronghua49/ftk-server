package com.risepu.ftk.web.m.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.risepu.ftk.server.domain.AdminUser;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.service.AdminService;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.utils.ConfigUtil;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.Constant;
import com.risepu.ftk.web.api.Response;

import net.lc4ever.framework.format.DateFormatter;

@RestController
@RequestMapping("/admin")
public class ManagerController {
	
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
	@PostMapping("/login")
	public ResponseEntity<Response<String>> orgLogin(@RequestParam(name = "name") String adminName,
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
	@PostMapping("/changePwd")
	public ResponseEntity<Response<String>> changePwd(@RequestParam String password,
			@RequestParam String newPwd, HttpServletRequest request)   {
		
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
	 * 根据参数查询企业信息
	 * @param key 关键字
	 * @param pageNo
	 * @param pageSize
	 * @param order 升降序参数 0：降序 1：升序
	 * @param state 审核状态
	 * @param request
	 * @return
	 */
	@GetMapping("/queryList/{pageNo:\\d+}")
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
		
		
		//Date startOfDay = DateFormatter(DateFormatter.nextDay(endDate));
		
		
		PageResult<Organization> pageResult = organizationService.findByParam(map, pageNo, pageSize);
		
		return ResponseEntity.ok(Response.succeed(pageResult));
		
	}
	
	/**
	 * 保存修改后的企业信息
	 * @param orgnization
	 * @return
	 */
	@PostMapping("/checkOrg")
	public ResponseEntity<Response<String>> checkOrgInfo(@RequestBody Organization organization){
		
		organizationService.saveOrUpdateOrgInfo(organization);
		
		return ResponseEntity.ok(Response.succeed("审核成功"));
		
	}
	
	
	@GetMapping("/queryOne/{orgId:\\w+}")
	public ResponseEntity<Response<Organization>> queryOrgById(@PathVariable String orgId ){
		
		Organization organization = organizationService.findAuthenOrgById(orgId);
		return ResponseEntity.ok(Response.succeed(organization));
	}

	
	private AdminUser getCurrAdmin(HttpServletRequest request) {
		return (AdminUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
	}
	

	
}
