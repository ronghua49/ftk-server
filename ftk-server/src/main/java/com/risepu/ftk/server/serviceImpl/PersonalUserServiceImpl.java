package com.risepu.ftk.server.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.risepu.ftk.server.domain.PersonalUser;

import net.lc4ever.framework.service.GenericCrudService;

@Service
public class PersonalUserServiceImpl implements PersonalUserService {

	@Autowired
	private GenericCrudService crudService;

	@Override
	public String personReg(String phone, String id) {
		PersonalUser personalUser = new PersonalUser();
		personalUser.setId(id);
		personalUser.setMobile(phone);
		crudService.save(personalUser);
		return "success";
	}

	@Override
	public boolean isBandPhone(String mobile) {
		PersonalUser user = crudService.uniqueResultByProperty(PersonalUser.class, "mobile", mobile);
		if (user!=null) {
			return true;
		}
		return false;
	}

	@Override
	public PersonalUser personLogin(String mobile, String inCode, String createCode) {
		if(inCode.equals(createCode)) {
			return crudService.uniqueResultByProperty(PersonalUser.class, "mobile", mobile);
		}
		return null;
	}

	

}
