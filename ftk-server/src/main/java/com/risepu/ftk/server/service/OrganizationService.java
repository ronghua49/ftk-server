package com.risepu.ftk.server.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.web.b.dto.LoginResult;

//@Remote(path="/org")
public interface OrganizationService {
	
	 /**
	  * 
	  * @param phone 手机号
	  * @param password 密码
	  * @return
	  */
	public String orgReg(String phone,String password);
	
	/**
	 *  
	 * @param phoneOrName 
	 * @param password
	 * @return LoginResult 登录返回结果
	 * 
	 */
	public LoginResult orgLogin(String phoneOrName,String password); 
	
	/**
	 * 
	 * @param id 手机号
	 * @param newPwd 新密码
	 */
	public void changePwd(String id,String newPwd);
	
	
	/**
	 * 上传图片
	 * @param file
	 * @return 图片的存储名
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public String upload (MultipartFile file) throws IllegalStateException, IOException;
	
	/**
	 * 保存
	 * @param organization 认证的企业信息
	 * @return 是否发送成功
	 */
	public boolean saveOrUpdateOrgInfo(Organization organization);

	/**
	 * 判断企业用户名是否存在
	 * @param mobileOrName
	 * @return 存在返回id值，否则返回 null
	 */
	public String  checkOrgName(String mobileOrName);
	
	
	

}
