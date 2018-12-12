package com.risepu.ftk.server.service;

public interface SmsService {

	public String sendCode(String phone);
	
	public boolean identify(String inCode,String createCode );
	
}
