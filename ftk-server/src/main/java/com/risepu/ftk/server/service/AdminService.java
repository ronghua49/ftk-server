package com.risepu.ftk.server.service;

import com.risepu.ftk.server.domain.AdminUser;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.utils.PageResult;

//@Remote(path = "/admin")
public interface AdminService {
	
	/**
	 * 根据用户名查询管理员
	 * @param userName 用户名
	 * @return
	 */
	public AdminUser findAdminByName(String userName);
	
	
	/**
	 * 更新管理员信息
	 * @param adminUser
	 */
	public void updateAdminUser(AdminUser adminUser);

	
//	
//	/**
//	 * 查询发起认证的企业信息
//	 * @param key 关键字
//	 * @param pageNo 页码
//	 * @param pageSize 显示条数
//	 * @return
//	 */
//	public PageResult<Organization> queryAuthByParam(String key, Integer pageNo, Integer pageSize);
//
//	
//	/**
//	 * 保存修改后的企业信息
//	 * @param organization 修改后的企业信息
//	 */
//	public void saveCheckedInfo(Organization organization);
//	
	
	
	
	
}
