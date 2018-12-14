package com.risepu.ftk.server.service;

import net.lc4ever.framework.remote.annotation.Remote;

@Remote(path="/sms")
public interface SmsService {

	public String sendCode(String phone);
	
	public boolean identify(String inCode,String createCode );
	
}
