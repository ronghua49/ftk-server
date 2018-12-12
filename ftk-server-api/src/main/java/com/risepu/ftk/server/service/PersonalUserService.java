package com.risepu.ftk.server.service;

import net.lc4ever.framework.remote.annotation.Remote;

@Remote(path="/person")
public interface PersonalUserService {

	public String personReg(String phone,String code,String password);
	
	
	public String personLoginUsePwd(String phone,String password);
	
	
	public String personLoginUseCode(String phone,String code);
	
}
