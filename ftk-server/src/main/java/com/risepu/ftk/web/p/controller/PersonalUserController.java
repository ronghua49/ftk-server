package com.risepu.ftk.web.p.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.risepu.ftk.server.domain.AuthorizationStream;
import com.risepu.ftk.server.domain.PersonalUser;
import com.risepu.ftk.server.service.PersonalUserService;
import com.risepu.ftk.server.service.SmsService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.Constant;
import com.risepu.ftk.web.ScanRequest;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.p.dto.AuthHistoryInfo;
import com.risepu.ftk.web.p.dto.LoginRequest;
import com.risepu.ftk.web.p.dto.LoginResult;
import com.risepu.ftk.web.p.dto.RegistRequest;


@RestController
@RequestMapping("/personal")
public class PersonalUserController implements Controller {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PersonalUserService personalService;
	
	@Autowired
	private SmsService SmsService;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	
	/**
	 * 用户登录　通过扫描二维码跳转登录页面
	 * @param loginRequest 
	 * @param request
	 * @return
	 */
	public ResponseEntity<Response<String>> personalLogin(@RequestBody LoginRequest loginRequest,HttpServletRequest request){
		
		boolean codeIdentify = codeIdentify(request,loginRequest.getInCode());
		
		if(codeIdentify) {
			PersonalUser personalUser = personalService.personLogin(loginRequest.getPhone());
			request.getSession().setAttribute(Constant.getSessionCurrUser(), personalUser);
			logger.debug("用户手机号--{}，登录成功",loginRequest.getPhone());
			return ResponseEntity.ok(Response.succeed("登录成功"));
		}else {
			return ResponseEntity.ok(Response.failed(001, "验证码输入错误"));
		}
		
		
	}


	/**
	 * 用户发起授权或拒绝
	 * @param scanRequest 请求数据
	 * @param request
	 * @return
	 */
	@PostMapping("/auth")
	public ResponseEntity<Response<String>> personAuth(@RequestBody ScanRequest scanRequest,HttpServletRequest request) {
		
		PersonalUser personalUser = (PersonalUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
		/**  增加授权流水记录*/
		AuthorizationStream authStream = new AuthorizationStream();
		authStream.setUserId(personalUser.getId());
		authStream.setOrgId(scanRequest.getOrgId());
		
		/** 判断授权 */
		if(scanRequest.getState().equals("0")) {
			/** 发送验证码 */
			SmsService.sendCode(personalUser.getMobile());
			authStream.setState(AuthorizationStream.AUTH_STATE_PASS);
			return ResponseEntity.ok(Response.succeed("授权码下发成功"));
			
		}else {
			authStream.setState(AuthorizationStream.AUTH_STATE_REFUSE);
		}
		
		personalService.saveAuthStream(authStream);
		return null;
	}
	
	/**
	 * 查询历史授权记录
	 * @param key 关键字
	 * @param pageNo 查询页码
	 * @param pageSize 显示条数
	 * @return
	 */
//	public ResponseEntity<Response<PageResult<AuthHistoryInfo>>> getAuthInfoList(@RequestParam String key,@RequestParam(defaultValue="1") Integer pageNo,@RequestParam Integer pageSize){
//		
//		
//		PageResult<AuthHistoryInfo> pageResult =  personalService.queryHistoryByParam(key,pageNo,pageSize);
//		
//		
//		
//		
//		
//		
//	}

	private boolean codeIdentify(HttpServletRequest request, String inCode) {
		
		if(inCode.equals(request.getSession().getAttribute(Constant.getSessionVerificationCodeSms()))) {
			return true;
		}
		return false;
	}
	
	

}
