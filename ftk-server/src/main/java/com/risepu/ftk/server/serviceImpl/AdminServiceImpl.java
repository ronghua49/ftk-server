package com.risepu.ftk.server.serviceImpl;

import com.risepu.ftk.server.domain.AdminUser;
import com.risepu.ftk.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.lc4ever.framework.service.GenericCrudService;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private GenericCrudService crudService;

	@Override
	public AdminUser findAdminByName(String userName) {
		return crudService.uniqueResultByProperty(AdminUser.class, "id", userName);
	}

	@Override
	public void updateAdminUser(AdminUser adminUser) {
		crudService.update(adminUser);
	}

	





	
	
	
	

}
