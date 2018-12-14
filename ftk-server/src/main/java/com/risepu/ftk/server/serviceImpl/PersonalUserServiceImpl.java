package com.risepu.ftk.server.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risepu.ftk.server.domain.AuthorizationStream;
import com.risepu.ftk.server.domain.PersonalUser;
import com.risepu.ftk.server.service.PersonalUserService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.p.dto.AuthHistoryInfo;

import net.lc4ever.framework.service.GenericCrudService;

@Service
public class PersonalUserServiceImpl implements PersonalUserService {

	@Autowired
	private GenericCrudService crudService;

	@Override
	public String personReg(String mobile, String cardNo, String userName) {
		PersonalUser personalUser = new PersonalUser();
		personalUser.setId(cardNo);
		personalUser.setMobile(mobile);
		personalUser.setUserName(userName);
		crudService.save(personalUser);
		return "success";
	}

	@Override
	public PersonalUser personLogin(String mobile) {
		PersonalUser personalUser = crudService.uniqueResultByProperty(PersonalUser.class, "mobile", mobile);
		/** 若果用户不存在*/ //TODD
		if(personalUser==null) {
			PersonalUser personalUser2 = new PersonalUser();
			personalUser2.setMobile(mobile);
			crudService.save(personalUser2);
			return personalUser2;
		}
		
		return personalUser;
	}

	@Override
	public void saveAuthStream(AuthorizationStream authStream) {
		crudService.save(authStream);
	}

	@Override
	public PageResult<AuthHistoryInfo> queryHistoryByParam(Map<String, Object> paramMap, Integer pageNo, Integer pageSize) {
		
		/** 查询所有历史授权记录*/
		
		//crudService.queryByPropertie(AuthHistoryInfo.class, (pageNo-1)*pageSize, pageSize, "name", paramMap.get("orgName"));
		
		
		
		return null;
	}

}
