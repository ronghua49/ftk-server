package com.risepu.ftk.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.risepu.ftk.server.service.SmsService;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.dto.MesRequest;

@Controller
@RequestMapping("/api/sms")
public class SmsController implements SmsControllerApi {

	@Autowired
	private SmsService smsService;

	@Override
	public ResponseEntity<Response<String>> sendSms(MesRequest mesRequest, HttpServletRequest request) {

		System.out.println("发送短信的请求sessionid:" + request.getSession().getId());

		String sessionCode = (String) request.getSession().getAttribute(Constant.getSessionVerificationCodeImg());

		boolean identify = smsService.identify(mesRequest.getImgCode(), sessionCode);

		if (identify) {
			String sendCode = smsService.sendCode(mesRequest.getPhone());

			request.getSession().setAttribute(Constant.getSessionVerificationCodeSms(), sendCode);
			return ResponseEntity.ok(Response.succeed("验证码已下发"));
		} else {
			return ResponseEntity.ok(Response.failed(1, "图片验证码错误"));
		}

	}

}
