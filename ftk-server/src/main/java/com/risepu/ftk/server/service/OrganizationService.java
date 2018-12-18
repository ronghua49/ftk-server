package com.risepu.ftk.server.service;

import net.lc4ever.framework.remote.annotation.Remote;

@Remote(path="/org")
public interface OrganizationService {

	 /*注册*/
	 public String orgReg(String phone,String code,String password);

	/*登录*/
	public String orgLogin(String phoneOrName,String password);




}
