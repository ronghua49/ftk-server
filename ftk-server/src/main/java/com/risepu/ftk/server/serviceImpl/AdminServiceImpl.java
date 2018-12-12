package com.risepu.ftk.server.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.risepu.ftk.server.domain.AdminUser;

import net.lc4ever.framework.service.GenericCrudService;

public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private GenericCrudService crudService;

	public void setCrudService(GenericCrudService crudService) {
		this.crudService = crudService;
	}


	@Override
	public AdminUser login(String adminName, String password) {
		AdminUser adminUser = crudService.uniqueResultHql(AdminUser.class, "from AdminUser where adminName=?1", adminName);
		if(adminUser!=null && adminUser.getPassword().equals(password)) {
			return adminUser;
		}
		return null;
	}


	@Override
	public String changePwd(AdminUser adminUser) {
		crudService.update(adminUser);
		return "success";
	}
	
	
	
	
	

}
