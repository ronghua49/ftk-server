package com.risepu.ftk.web.b.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationAdvice;
import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.web.Constant;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.ForgetRequest;
import com.risepu.ftk.web.b.dto.LoginResult;
import com.risepu.ftk.web.b.dto.RegistRequest;
import com.risepu.ftk.web.b.dto.RegistResult;

@Controller
@RequestMapping("/org")
public class OrganizationController   {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrganizationService organizationService;
	
	
	/**
	 * 企业端注册
	 * @return
	 */
	
	@PostMapping("/regist")
	@ResponseBody
	public ResponseEntity<Response<RegistResult>> orgRegist(@RequestBody RegistRequest registVo, HttpServletRequest request) {
		// 判断smsCode
		RegistResult registResult = new RegistResult();

		String code = (String) request.getSession().getAttribute(Constant.getSessionVerificationCodeSms());

		/** 判断企业是否已经注册 */
		String orgId = organizationService.checkOrgName(registVo.getMobile());

		if (orgId == null) {
			if (registVo.getSmsCode().equals(code)) {
				organizationService.orgReg(registVo.getMobile(), registVo.getPassword());
				logger.debug("企业用户手机号--{},注册成功！", registVo.getMobile());
				registResult.setMessage("注册成功！");

			} else {
				registResult.setMessage("验证码输入错误！");
			}
		} else {
			registResult.setMessage("该企业已经注册，请直接登录");
		}
		return ResponseEntity.ok(Response.succeed(registResult));
	}

	
	/**
	 * 企业登录
	 * @param mobileOrName
	 *            手机号或者企业名
	 * @param password
	 * @param request
	 * @return 登录结果
	 */

	//@Override
	@PostMapping("/login")
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<Response<LoginResult>> orgLogin(@RequestParam (name="name")String mobileOrName, @RequestParam String password,
			HttpServletRequest request) {
		LoginResult loginResult = organizationService.orgLogin(mobileOrName, password);

		if (loginResult.isSuccess()) {

			request.getSession().setAttribute(Constant.getSessionCurrUser(), loginResult.getOrganizationUser());
			logger.debug("企业用户手机号--{},登录成功！", loginResult.getOrganizationUser().getId());
			return ResponseEntity.ok(Response.succeed(loginResult));
		} else {
			return ResponseEntity.ok(Response.failed(001, loginResult.getMessage()));
		}
	}

	/**
	 * 忘记密码
	 * @param forgetRequest  表单数据
	 * @param request
	 * @return
	 */
	@PostMapping("/forgetPwd")
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<Response<String>> orgForgetPwd(@RequestBody ForgetRequest forgetRequest, HttpServletRequest request) {
		/** 判断输入的企业信息是否存在 */
		String orgId = organizationService.checkOrgName(forgetRequest.getMobileOrName());

		if (orgId != null) {
			String smsCode = (String) request.getSession().getAttribute(Constant.getSessionVerificationCodeSms());

			if (forgetRequest.getSmsCode().equals(smsCode)) {

				organizationService.changePwd(orgId, forgetRequest.getPassword());
				logger.debug("企业用户   {}，修改密码成功！", forgetRequest.getMobileOrName());

				return ResponseEntity.ok(Response.succeed("密码修改成功"));
			} else {

				return ResponseEntity.ok(Response.failed(001, "验证码输入错误"));
			}
		} else {
			return ResponseEntity.ok(Response.failed(002, "该账号还未注册"));
		}
	}
	
	/**
	 * 修改密码
	 */
	@PostMapping("/changePwd")
	@ResponseBody
	
	public ResponseEntity<Response<String>> orgChangePwd(@RequestParam String password, @RequestParam String newpwd, HttpServletRequest request) {

		OrganizationUser org =getCurrUser(request);
		String message = "密码输入错误";
		String salt = ConfigUtils.getProperty("salt");
		
		password = DigestUtils.md5Hex(password+salt);
		newpwd = DigestUtils.md5Hex(newpwd+salt);
		
		if (org.getPassword().equals(password)) {
			message = "密码修改成功";
			organizationService.changePwd(org.getId(), newpwd);
			logger.debug("企业用户   {}，修改密码成功！", org.getId());
		}
		return ResponseEntity.ok(Response.succeed(message));

	}
	
	


	/**
	 * 图片上传
	 * @param file 图片文件流
	 * @return 保存的图片名
	 */
//	@Override
	@PostMapping("/img/upload")
	public ResponseEntity<Response<String>> upload(@RequestParam(name="file") MultipartFile file) {

		try {
			String fileName = organizationService.upload(file);
			return ResponseEntity.ok(Response.succeed(fileName));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(Response.failed(002, "上传失败"));
		}
	}

	/**
	 * 图片下载
	 * @param imgName  图片名称
	 * @param response
	 * @return
	 */
	@GetMapping("/img/download/{imgName:\\w+.[a-z]{0,4}}")
//	@Override
	public ResponseEntity<Response<String>> imgDownload( @PathVariable String imgName, HttpServletResponse response) {

		try {
			organizationService.download(imgName, response);
			return ResponseEntity.ok(Response.succeed("图片下载成功"));

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.ok(Response.failed(002, "图片下载失败"));
		}

	}
	
	/**
	 *校验当前企业的审核状态
	 * @param request
	 * @return 审核结果   0 未审核  1审核中  2审核通过  3审核失败  
	 */
	@GetMapping("/auth/check")
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<Response<Organization>> checkAuthState(HttpServletRequest request){
		
		OrganizationUser currUser = getCurrUser(request);
		Organization org = organizationService.checkAuthState(currUser.getId());
		
		return ResponseEntity.ok(Response.succeed(org));
	}
	
	/**
	 * 保存企业认证信息
	 * 
	 * @param organization
	 *            上传的的企业信息
	 * @param request
	 * @return 上传结果
	 */
//	@Override
	@PostMapping("/authen/info")
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<Response<String>> orgAuthen(@RequestBody Organization organization,
			HttpServletRequest request) {
		
		OrganizationUser user = (OrganizationUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
		
		/** 增加关联 id 为发起认证的企业用户手机号 */
		//organization.setId(user.getId());
		organization.setId(user.getId());
		
		organization.setState(Organization.CHECKING_STATE);
		
		organizationService.saveOrUpdateOrgInfo(organization);
		//logger.debug("企业用户手机号--{},发送认证信息成功！", user.getId());
		return ResponseEntity.ok(Response.succeed("资料上传成功，等待审核"));
		
	}
	
	
	/**
	 * 企业扫码单据
	 * @param orgId 企业id
	 * @param personId 用户id
	 * @return
	 */
	public ResponseEntity<Response<String>> scanQR(@RequestParam String orgId, @RequestParam String personId){
		
		//organizationService.InsertAuthorStream(orgId,personId);
		
		
		return ResponseEntity.ok(Response.succeed("流水产生成功！"));
	}
	
	
	
	
	/**
	 * 企业扫码验证单据信息
	 * @param authCode 授权码
	 * @param cardNo 用户身份证号
	 * @return 单据信息 集合
	 */
//	public ResponseEntity<Response<PageResult<String>>> identify(@RequestParam String authCode,@RequestParam String cardNo) {
//		chainService.verifyDocument(authCode,cardNo);
//		
//		
//	
//
//	}
	/**
	 * 企业反馈信息录入
	 * @param adviceRequest 企业反馈信息
	 * @return 保存结果
	 */
	@PostMapping("/advice")
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<Response<String>> adviceInfo(@RequestBody OrganizationAdvice advice,HttpServletRequest request){
		
		OrganizationUser currUser = getCurrUser(request);
		advice.setOrgId(currUser.getId());
		organizationService.saveAdviceInfo(advice);
		return ResponseEntity.ok(Response.succeed("意见反馈成功！"));
		
	}
	
	
	
	
	private OrganizationUser getCurrUser(HttpServletRequest request) {
		return (OrganizationUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
}

}
