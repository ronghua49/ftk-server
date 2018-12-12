package com.risepu.ftk.server.service;

import com.risepu.ftk.server.domain.AdminUser;

import net.lc4ever.framework.remote.annotation.Remote;

@Remote(path="/admin")
public interface AdminService {
	
	public AdminUser login(String adminName,String password); 
	
	//修改密码
	public String changePwd(AdminUser adminUser);

}
