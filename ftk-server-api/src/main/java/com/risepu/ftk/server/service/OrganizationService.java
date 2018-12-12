package com.risepu.ftk.server.service;

import java.util.Map;

import net.lc4ever.framework.remote.annotation.Remote;

@Remote(path="/org")
public interface OrganizationService {
	
	 /**
	  * 
	  * @param phone 手机号
	  * @param code 验证码
	  * @param password 密码
	  * @return
	  */
	public String orgReg(String phone,String code,String password);
	
	/**
	 *  
	 * @param phoneOrName 
	 * @param password
	 * @return map message： 登录返回信息
	 * 				org: 登录成功后的企业对象
	 * 
	 */
	public Map<String, Object> orgLogin(String phoneOrName,String password); 
	
	/**
	 * 
	 * @param phone 手机号
	 * @param newPwd 新密码
	 * @return
	 */
	public String changePwd(String phone,String newPwd);
	
	/**
	 * 企业认证
	 */
	
	
	

}
