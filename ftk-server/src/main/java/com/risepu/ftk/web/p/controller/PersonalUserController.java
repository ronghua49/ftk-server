package com.risepu.ftk.web.p.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.risepu.ftk.server.domain.AuthorizationStream;
import com.risepu.ftk.server.domain.PersonalUser;
import com.risepu.ftk.server.service.PersonalUserService;
import com.risepu.ftk.server.service.SmsService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.Constant;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.p.dto.AuthHistoryInfo;
import com.risepu.ftk.web.p.dto.LoginRequest;
import com.risepu.ftk.web.p.dto.LoginResult;


@RestController
@RequestMapping("/api/personal")
public class PersonalUserController implements PersonzalUserApi  {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PersonalUserService personalService;
	
	@Autowired
	private SmsService smsService;
	
//	@Autowired
//	private ChainService chainService;

	
	/**
	 * 用户登录　通过扫描二维码跳转登录页面
	 * @param loginRequest 
	 * @param request
	 * @return 需要
	 */
	@Override
	public ResponseEntity<Response<LoginResult>> personalLogin(LoginRequest loginRequest,HttpServletRequest request){
		
		
		String smsCode =getSmsCode(request);  
		
		boolean identify = smsService.identify(loginRequest.getInCode(), smsCode);
		
		LoginResult loginResult = new LoginResult();
		
		
		if(identify) {
			/** 解析单据信息 */
			//Integer documentId = chainService.praseDocumentData(loginRequest.getQrCode(),loginRequest.getDocumentHash());
			
			/** 根据文档id查询 文档数据*/
			
			/** 校验用户输入的身份证号是否和单据信息一致 */
			
			String  cardNo="410181199105232512";
			
			if(cardNo.equals(loginRequest.getCardNo())) {
				
				PersonalUser personalUser = personalService.findUserByNo(cardNo);
				
				if(personalUser!=null) {
					loginResult.setMessage("登录成功");
					loginResult.setPersonalUser(personalUser);
				}else {
					PersonalUser user = new PersonalUser();
					user.setId(cardNo);
					user.setMobile(loginRequest.getPhone());
					personalService.savePersonUser(user);
					personalUser= user;
				}
				request.getSession().setAttribute(Constant.getSessionCurrUser(), personalUser);
				
				/** 根据身份证 查询新的请求授权的企业名和 当前授权流水的id*/
				Map<String, Object> map  = personalService.findNewRequestByCardNo(cardNo);
				
				if(map!=null) {
					loginResult.setOrgName((String)map.get("orgName"));
					loginResult.setStreamId((Long)map.get("streamId"));;
				}


				logger.debug("用户手机号--{}，登录成功",personalUser.getMobile());
				
				return ResponseEntity.ok(Response.succeed(loginResult));
				
			}else {
				loginResult.setMessage("非本人单据，扫描无效");
				return ResponseEntity.ok(Response.failed(10, loginResult.getMessage()));
			}
			
		}else {
			loginResult.setMessage("验证码输入错误");
			return ResponseEntity.ok(Response.failed(2,loginResult.getMessage()));
		}
		
	}


	private String getSmsCode(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(Constant.getSessionVerificationCodeSms());
	}


	/**
	 * 用户发起授权或拒绝
	 * @param streamId 当前扫描流水id
	 * @param state 授权状态
	 * @param request
	 * @return
	 */
	@Override
	public ResponseEntity<Response<String>> personAuth( String streamId, String state, HttpServletRequest request) {
		
		String message ="";
		PersonalUser personalUser = getSession(request);
		
		AuthorizationStream stream =   personalService.findAuthorizationStreamById(Long.parseLong(streamId));
		
		if(stream==null) {
			return ResponseEntity.ok(Response.failed(11,"错误的流水id"));
		}
		
		/** 判断授权 */
		if(state.equals("0")) {
			/** 发送验证码 */
			smsService.sendCode(personalUser.getMobile());
			stream.setState(AuthorizationStream.AUTH_STATE_PASS);
			message="授权码下发成功";
			
		}else {
			stream.setState(AuthorizationStream.AUTH_STATE_REFUSE);
			message="授权已拒绝";
		}
		
		personalService.update(stream);
		
		return ResponseEntity.ok(Response.succeed(message));
	}



	private PersonalUser getSession(HttpServletRequest request) {
		return (PersonalUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
	}
	
	/**
	 * 查询历史授权记录
	 * @param key 关键字
	 * @param pageNo 查询页码
	 * @param pageSize 显示条数
	 * @return
	 */
	@Override
	public ResponseEntity<Response<PageResult<AuthHistoryInfo>>> getAuthInfoList(String key,  Integer pageNo, Integer pageSize,
																				 HttpServletRequest request){
		PersonalUser user = getCurrUser(request);
		
		PageResult<AuthHistoryInfo> pageResult =  personalService.queryHistoryByParam(key,pageNo,pageSize,user.getId());
		
		return ResponseEntity.ok(Response.succeed(pageResult));
		
	}




	private PersonalUser getCurrUser(HttpServletRequest request) {
		return (PersonalUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
	}

}
