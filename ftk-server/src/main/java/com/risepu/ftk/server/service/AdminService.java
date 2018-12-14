package com.risepu.ftk.server.service;

import com.risepu.ftk.server.domain.AdminUser;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.utils.PageResult;

//@Remote(path = "/admin")
public interface AdminService {
	/** 登录*/
	public AdminUser login(String adminName, String password);

	/** 修改密码 */
	public String changePwd(AdminUser adminUser);
	
	/**
	 * 查询发起认证的企业信息
	 * @param key 关键字
	 * @param pageNo 页码
	 * @param pageSize 显示条数
	 * @return
	 */
	public PageResult<Organization> queryAuthByParam(String key, Integer pageNo, Integer pageSize);

	/**
	 * 保存修改后的企业信息
	 * @param organization 修改后的企业信息
	 */
	public void saveAdterEdit(Organization organization);
	
	
	
	//public List<>
	
	
	
}
