package com.risepu.ftk.server.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risepu.ftk.server.domain.AuthorizationStream;
import com.risepu.ftk.server.domain.Organization;
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
	public PersonalUser personLogin(String cardNo,String phone) {
		
		PersonalUser user = new PersonalUser();
		user.setMobile(phone);
		user.setId(cardNo);
		crudService.save(user);
		return user;
		
		
	}

	@Override
	public void update(AuthorizationStream authStream) {
		crudService.update(authStream);
	}

	@Override
	public PageResult<AuthHistoryInfo> queryHistoryByParam(String key, Integer pageNo, Integer pageSize,String personId) {
		
		List<AuthHistoryInfo> historyList = new ArrayList<AuthHistoryInfo>();
		
		
		/** 根据当前用户身份证号查询所有授权流水记录*/
		List<AuthorizationStream> streamList = crudService.hql(AuthorizationStream.class, "from AuthorizationStream where personId =?1 and state in (1,2) ", personId);
		
		for(AuthorizationStream stream :streamList) {
			AuthHistoryInfo history = new AuthHistoryInfo();
			Organization organization = crudService.uniqueResultByProperty(Organization.class, "id", stream.getOrgId());
			history.setAuthTime(stream.getModifyTimestamp());
			history.setOrgName(organization.getName());
			history.setAuthState(stream.getState());
			history.setOrgAddress(organization.getAddress());
			history.setOrgTel(organization.getTel());
			
			historyList.add(history);
			
		}
		
		PageResult<AuthHistoryInfo> page = new PageResult<>();
		
		page.setCount(pageSize);
		page.setData(historyList);
		
		return page;
	}

	@Override
	public Map<String,Object> findNewRequestByCardNo(String cardNo) {
		
		List<AuthorizationStream> streams = crudService.hql(AuthorizationStream.class, "from AuthorizationStream where personalCardNo =?1 and state=0 order by createTimestamp desc", cardNo);
		
		if(streams!=null && !streams.isEmpty()) {
			/** 只查询最近扫描单据的一个企业id */
			String orgId = streams.get(0).getOrgId();
			
			Organization organization = crudService.uniqueResultByProperty(Organization.class, "id", orgId);
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("orgName", organization.getName());
			map.put("streamId", streams.get(0).getId());
			
			return map ;
		}
		return null;
	}

	@Override
	public AuthorizationStream findAuthorizationStreamById(Integer streamId) {
		return crudService.uniqueResultByProperty(AuthorizationStream.class, "id", streamId);
	}

}
