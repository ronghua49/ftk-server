package com.risepu.ftk.web.b.controller;

import com.risepu.ftk.server.domain.*;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.server.service.ProofDocumentService;
import com.risepu.ftk.utils.ConfigUtil;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.Constant;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class OrganizationController implements OrganizationApi{

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrganizationService organizationService;


	@Autowired
	private ProofDocumentService proofDocumentService;

	/**
	 * 企业端注册
	 * 
	 * @return
	 */
	@Override
	public ResponseEntity<Response<String>> orgRegist(RegistRequest registVo,
			HttpServletRequest request) {

		// 判断smsCode

		String code = (String) request.getSession().getAttribute(Constant.getSessionVerificationCodeSms());

		if (registVo.getSmsCode().equals(code)) {

			/** 判断企业是否已经注册 */
			String orgId = organizationService.checkOrgName(registVo.getMobile());

			if (orgId == null) {
				organizationService.orgReg(registVo.getMobile(), registVo.getPassword());
				logger.debug("企业用户手机号--{},注册成功！", registVo.getMobile());
				
				return ResponseEntity.ok(Response.succeed("注册成功！"));

			} else {
				return ResponseEntity.ok(Response.failed(3, "该企业已经注册，请直接登录"));
			}
		} else {
			return ResponseEntity.ok(Response.failed(2, "验证码输入错误！"));
		}
	}
	/**
	 * 企业登录
	 * 
	 * @param loginRequest 登录请求
	 *
	 * @return 登录结果
	 * @throws  
	 */

	@Override
	public ResponseEntity<Response<LoginResult>> orgLogin(OrgLoginRequest loginRequest, HttpServletRequest request)   {
		
			LoginResult loginResult = organizationService.orgLogin(loginRequest.getName(), loginRequest.getPassword());
			
			if(loginResult.getCode()==0) {
				/** 设置session对象为 企业用户对象 */
				setCurrUserToSession(request,loginResult.getOrganizationUser());
				logger.debug("企业用户--{},登录成功！", loginRequest.getName());
				return ResponseEntity.ok(Response.succeed(loginResult));
			}
			
			return ResponseEntity.ok(Response.failed(loginResult.getCode(), loginResult.getMessage()));
	}
	
	
	private void setCurrUserToSession(HttpServletRequest request, OrganizationUser organizationUser) {
		request.getSession().setAttribute(Constant.getSessionCurrUser(), organizationUser);
	}

	/**
	 * 忘记密码
	 * @param forgetRequest  表单数据
	 *           
	 * @param request 请求对象
	 * @return
	 */
	@Override
	public ResponseEntity<Response<String>> orgForgetPwd( ForgetRequest forgetRequest,
			HttpServletRequest request) {
		String salt = ConfigUtil.getValue("salt");
		/** 判断输入的企业信息是否存在  返回各自的id */
		String orgId = organizationService.checkOrgName(forgetRequest.getMobileOrName());

		if (orgId != null) {
			String smsCode = (String) request.getSession().getAttribute(Constant.getSessionVerificationCodeSms());

			if (forgetRequest.getSmsCode().equals(smsCode)) {

				String newPwd =  DigestUtils.md5Hex(forgetRequest.getPassword() + salt);
				organizationService.changePwd(orgId, newPwd);
				logger.debug("企业用户   {}，修改密码成功！", forgetRequest.getMobileOrName());

				return ResponseEntity.ok(Response.succeed("密码修改成功"));
			} else {

				return ResponseEntity.ok(Response.failed(2, "验证码输入错误"));
			}
			
		} else {
			return ResponseEntity.ok(Response.failed(4, "该账号还未注册,请先注册"));
		}
	}

	/**
	 * 修改密码
	 */
	@Override
	public ResponseEntity<Response<String>> orgChangePwd( String password,  String newpwd,
			HttpServletRequest request) {


		OrganizationUser currUser = getCurrUser(request);
		OrganizationUser user = organizationService.findOrgUserById(currUser.getId());
		
		String salt = ConfigUtil.getValue("salt");
		password = DigestUtils.md5Hex(password + salt);
		newpwd = DigestUtils.md5Hex(newpwd + salt);

		if (user.getPassword().equals(password)) {
			organizationService.changePwd(user.getId(), newpwd);
			logger.debug("企业用户   {}，修改密码成功！", user.getId());
			return ResponseEntity.ok(Response.succeed("密码修改成功"));
		}else {
			return ResponseEntity.ok(Response.failed(7, "修改失败，输入密码和服务端密码不一致"));
		}
	}

	/**
	 * 图片上传
	 * 
	 * @param file
	 *            图片文件流
	 * @return 保存的图片名
	 */
	@Override
	public ResponseEntity<Response<String>> upload( MultipartFile file) {

		try {
			String fileName = organizationService.upload(file);
			return ResponseEntity.ok(Response.succeed(fileName));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(Response.failed(8, "上传失败"));
		}
	}

	/**
	 * 图片下载
	 * 
	 * @param imgName
	 *            图片名称
	 * @param response
	 * @return
	 */
	@Override
	public ResponseEntity<Response<String>> imgDownload(@PathVariable String imgName, HttpServletResponse response) {

		try {
			organizationService.download(imgName, response);
			return ResponseEntity.ok(Response.succeed("图片下载成功"));

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.ok(Response.failed(9, "图片下载失败"));
		}

	}

	/**
	 * 校验当前企业的审核状态
	 * @param request
	 * @return Organization 企业的信息 (若为空则未审核)
	 */
	@Override
	public ResponseEntity<Response<Organization>> checkAuthState(HttpServletRequest request) {

		OrganizationUser currUser = getCurrUser(request);
		
		/** 为空 未认证   不为空  state 判断*/
		Organization org =null;
		if(currUser.getOrganizationId()!=null){
			org = organizationService.findAuthenOrgById(currUser.getOrganizationId());
		}

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
	@Override
	public ResponseEntity<Response<String>> orgAuthen( Organization organization,
			HttpServletRequest request) {

		OrganizationUser user = getCurrUser(request);
		Organization currOrg = organizationService.findAuthenOrgById(user.getOrganizationId());
		Organization org = organizationService.findAuthenOrgById(organization.getId());

		if(currOrg!=null&&currOrg.getState().equals(Organization.CHECK_FAIL_STATE)){
			/** 表示修改时组织机构代码证和审核成功的或者审核中的重复*/
			if(!user.getOrganizationId().equals(organization.getId())&&org!=null&&!org.getState().equals(Organization.CHECK_FAIL_STATE)){
				return ResponseEntity.ok(Response.failed(400,"该组织机构代码证书已经被注册，不得重复！"));
			}
			currOrg.setId(organization.getId());
			currOrg.setAddress(organization.getAddress());
			currOrg.setLicenseImgName(organization.getLicenseImgName());
			currOrg.setLegalPerson(organization.getLegalPerson());
			currOrg.setName(organization.getName());
			currOrg.setTel(organization.getTel());
            currOrg.setState(Organization.CHECKING_STATE);
            organizationService.updateOrg(currOrg);

        }else{
			/** 表示第一提交组织机构代码证重复*/
			if(org!=null&&!org.getState().equals(Organization.CHECK_FAIL_STATE)){
				return ResponseEntity.ok(Response.failed(400,"该组织机构代码证书已经被注册，不得重复！"));
			}
			/** 增加关联 id 为发起认证的企业用户手机号 */
			user.setOrganizationId(organization.getId());
			organizationService.updateOrgUser(user);

            organization.setState(Organization.CHECKING_STATE);
            organizationService.save(organization);
        }
		logger.debug("企业用户手机号--{},发送认证信息成功！", user.getId());
		return ResponseEntity.ok(Response.succeed("资料上传成功，等待审核"));
	}

	/**
	 * 企业扫码单据 产生扫码流水(在跳转输入授权码之前)
	 * 
	 * @param cardNo
	 *            用户身份证号
	 * @return
	 */
	@Override
	public ResponseEntity<Response<String>> scanQR( String cardNo,HttpServletRequest request) {
		
		/** 未审核通过的企业不允许扫描单据 */
		OrganizationUser currUser = getCurrUser(request);
		
		Organization org = organizationService.findAuthenOrgById(currUser.getId());
		/** 只有审核通过后的企业才可以扫描单据 */
		if(org!=null && org.getState().equals(Organization.CHECK_PASS_STATE)) {
			
			organizationService.InsertAuthorStream(currUser.getId(), cardNo);
			
			return ResponseEntity.ok(Response.succeed("流水产生成功！"));
			
		}
		return ResponseEntity.ok(Response.succeed("请审核通过后扫描"));
	}


	/**
	 * 企业扫码验单历史查询
	 * @param pageRequest 分页请求参数
	 * @param request
	 * @return
	 */
	@Override
	 public ResponseEntity<Response<PageResult>> verifyHistory(@RequestBody PageRequest pageRequest, HttpServletRequest request) {


		OrganizationUser orgUser = getCurrUser(request);

		/** 根据企业id查询 已经验证成功的流水idlist*/
		List<AuthorizationStream>  streams = organizationService.querySucceedAuthStreamByOrgId(orgUser.getOrganizationId());
		Set<String> chainHashs = new HashSet<>();

		for(AuthorizationStream stream:streams){
				chainHashs.add(stream.getChainHash());
		}



		//TODO 根据chainHash查找 证明文档对应的模板字段数据内容
		//PageResult  page = proofDocumentService.

		
		return null;
	
	 }

	/**
	 * 企业开单历史单据查询
	 * @param pageRequest 分页请求参数
	 * @param request
	 * @return
	 */
	@Override
	public ResponseEntity<Response<PageResult>> documentHistory(@RequestBody PageRequest pageRequest,HttpServletRequest request) {
		/** 查询企业开单历史 */
		//TODO
		OrganizationUser currUser = getCurrUser(request);

		Organization org = organizationService.findAuthenOrgById(currUser.getOrganizationId());
		PageResult document = proofDocumentService.getDocument(org.getId(), pageRequest.getPageNo(), pageRequest.getPageSize(), pageRequest.getKey());
		return ResponseEntity.ok(Response.succeed(document));
	}
	
	
	/**
	 * 验证单据是否合格
	 * @param qrCardNo 所扫描的二维码 用户身份证号
	 * @param inputCardNo 输入的身份证号
	 * @param streamId 当前扫描二维码的流水id
	 * @return
	 */
	@PostMapping("/qualify")
	public ResponseEntity<Response<PageResult<DocumentInfo>>> qualifyQRCode(@RequestParam String streamId ,@RequestParam String qrCardNo, @RequestParam String inputCardNo) {
		//TODO

		return  null;
	}
	
	
	
	/**
	 * 企业反馈信息录入
	 * 
	 * @param advice
	 *            企业反馈信息
	 * @return 保存结果
	 */
	@Override
	public ResponseEntity<Response<String>> adviceInfo(OrganizationAdvice advice,
			HttpServletRequest request) {

		OrganizationUser currUser = getCurrUser(request);
		
		advice.setOrgId(currUser.getId());
		organizationService.saveAdviceInfo(advice);
		return ResponseEntity.ok(Response.succeed("意见反馈成功！"));

	}


	
	private  OrganizationUser getCurrUser(HttpServletRequest request) {
		 return(OrganizationUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
	}

	
	/**
	 * 退出登录
	 */
	@Override
	public ResponseEntity<Response<String>> loginOut(HttpServletRequest request){
		
		request.getSession().setAttribute(Constant.getSessionCurrUser(),null);

		return ResponseEntity.ok(Response.succeed("退出登录成功"));
	}

	@Override
	public ResponseEntity<Response<String>> setDefaultTemplate(String  templateId,HttpServletRequest request) {

		OrganizationUser currUser = getCurrUser(request);
		Organization org = organizationService.findAuthenOrgById(currUser.getOrganizationId());
		org.setDefaultTemId(Long.parseLong(templateId));

		organizationService.updateOrg(org);
		return ResponseEntity.ok(Response.succeed("设置默认模板成功"));
	}


}
