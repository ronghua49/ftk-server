package com.risepu.ftk.server.service;

import com.risepu.ftk.server.domain.PersonalUser;

import net.lc4ever.framework.remote.annotation.Remote;

@Remote(path="/person")
public interface PersonalUserService {
	/**
	 * 个人用户注册
	 * @param phone 电话
	 * @param id 身份证号
	 * @return 
	 */
	public String personReg(String mobile,String id);
	
	/**
	 * 在下发验证码前校验手机号是否存在
	 * @param phnoe 
	 * @return
	 */
	public boolean isBandPhone(String mobile);
	
	/**
	 * 用户通过验证码登录
	 * @param phone 
	 * @param code
	 * @return
	 */
	public PersonalUser personLogin(String mobile,String inCode,String createCode);
	
}
