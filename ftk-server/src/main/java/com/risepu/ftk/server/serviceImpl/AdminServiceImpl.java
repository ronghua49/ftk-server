package com.risepu.ftk.server.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risepu.ftk.server.domain.AdminUser;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.service.AdminService;
import com.risepu.ftk.utils.PageResult;

import net.lc4ever.framework.service.GenericCrudService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private GenericCrudService crudService;

	@Override
	public AdminUser login(String adminName, String password) {
		AdminUser adminUser = crudService.uniqueResultHql(AdminUser.class, "from AdminUser where adminName=?1", adminName);
		if (adminUser != null && adminUser.getPassword().equals(password)) {
			return adminUser;
		}
		return null;
	}

	@Override
	public String changePwd(AdminUser adminUser) {
		crudService.update(adminUser);
		return "success";
	}

	@Override
	public PageResult<Organization> queryAuthByParam(String key, Integer pageNo, Integer pageSize) {
		int firstIndex = (pageNo - 1) * pageSize;
		PageResult<Organization> pageResult = new PageResult<>();
		List<Organization> orgList = crudService.hql(Organization.class, firstIndex, pageSize, "from Organization where name like ?1 order by createTimestamp desc", "%" + key + "%");
		pageResult.setCount(orgList.size());
		pageResult.setData(orgList);
		return pageResult;
	}

	@Override
	public void saveAdterEdit(Organization organization) {
		crudService.update(organization);
	}

}
