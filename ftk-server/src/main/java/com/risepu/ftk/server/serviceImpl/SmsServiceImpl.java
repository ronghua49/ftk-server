package com.risepu.ftk.server.serviceImpl;

import org.springframework.stereotype.Service;

import com.risepu.ftk.server.service.SmsService;

@Service
public class SmsServiceImpl implements SmsService {

	@Override
	public String sendCode(String phone) {
		// TODO Auto-generated method stub
		return "123456";
	}

	@Override
	public boolean identify(String inCode, String createCode) {
		if(inCode.equals(createCode)) {
			return true;
		}
		return false;
	}

}
