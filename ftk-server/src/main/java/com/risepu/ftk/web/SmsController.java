package com.risepu.ftk.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.risepu.ftk.server.service.SmsService;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.dto.MesRequest;

@Controller
@RequestMapping("/sms")
public class SmsController {
	
	@Autowired
	private SmsService smsService;
	
	@PostMapping("/get")
	@CrossOrigin
	public ResponseEntity<Response<String>> sendSms(@RequestBody MesRequest mesRequest,HttpServletRequest request){
		
		System.out.println("发送短信的请求sessionid:"+request.getSession().getId());
		
		String  sessionCode = (String)request.getSession().getAttribute(Constant.getSessionVerificationCodeImg());
		
		boolean identify = smsService.identify(mesRequest.getImgCode(), sessionCode);
		
		if(identify) {
			return ResponseEntity.ok(Response.succeed("验证码已下发"));
		}else {
			return ResponseEntity.ok(Response.failed(400, "图片验证码错误"));
		}
		
	}
	
	
	

}
