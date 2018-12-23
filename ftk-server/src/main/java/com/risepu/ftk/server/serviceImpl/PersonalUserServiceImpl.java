package com.risepu.ftk.server.serviceImpl;

import java.util.*;

import org.joda.time.DateTime;
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
	public String savePersonUser(PersonalUser user) {
		return crudService.save(user);
	}

	@Override
	public void update(AuthorizationStream authStream) {
		crudService.update(authStream);
	}

	@Override
	public PageResult<AuthHistoryInfo> queryHistoryByParam(String key, Integer pageNo, Integer pageSize, String personId) {

		List<AuthHistoryInfo> historyList = new ArrayList<>();
		String sql = "select a.STATE,a.MODIFY_TIMESTAMP,o.ADDRESS,o.`NAME`,o.TEL from ftk_authorization_stream a, ftk_organization o where a.ORG_ID=o.ORGANIZATION and a.PERSON_ID = ? and o.`NAME` like ? ORDER BY a.MODIFY_TIMESTAMP DESC";
		historyList = crudService.sql(AuthHistoryInfo.class, pageNo*pageSize, pageSize, sql, personId,"%"+key+"%");
		PageResult<AuthHistoryInfo> page = new PageResult<>();
		page.setTotalElements(historyList.size());
		page.setContent(historyList);

		return page;
	}

	@Override
	public Map<String, Object> findNewRequestByCardNo(String cardNo) {

		/** 只查询 10 分钟之内的 最近扫描单据的一个企业id */
		DateTime dateTime = new DateTime();
		DateTime minusTime = dateTime.minusMinutes(10);

		Date time = minusTime.toDate();
		List<AuthorizationStream> streams = crudService.hql(AuthorizationStream.class, "from AuthorizationStream where personId =?1 and state=0 and  createTimestamp > ?2 order by createTimestamp desc", cardNo, time);

		if (streams != null && !streams.isEmpty()) {

			String orgId = streams.get(0).getOrgId();

			Organization organization = crudService.uniqueResultByProperty(Organization.class, "id", orgId);

			Map<String, Object> map = new HashMap<>();
			map.put("orgName", organization.getName());
			map.put("streamId", streams.get(0).getId());

			return map;
		}
		return null;
	}

	@Override
	public AuthorizationStream findAuthorizationStreamById(long streamId) {
		return crudService.uniqueResultByProperty(AuthorizationStream.class, "id", streamId);
	}

	@Override
	public PersonalUser findUserByNo(String cardNo) {
		return crudService.uniqueResultByProperty(PersonalUser.class, "id", cardNo);
	}

}
