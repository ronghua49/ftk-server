/**
 *
 */
package com.risepu.ftk.server.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.risepu.ftk.server.domain.Organization;

import net.lc4ever.framework.service.GenericCrudService;

/**
 * @author q-wang
 */
@Service
public class SampleServiceImpl implements SampleService {

	private GenericCrudService crudService;

	public void setCrudService(GenericCrudService crudService) {
		this.crudService = crudService;
	}

	@Override
	public List<String> sample1() {

		List<Organization> orgs = crudService.list(Organization.class);
		List<Organization> orgs1 = crudService.hql(Organization.class, "from Organization where id = ?1", "12345678-1");
		Organization organization = crudService.uniqueResultHql(Organization.class, "from Organization where id = ?1", "12345678-1");

		organization = crudService.get(Organization.class, "123");

		organization.setName("");

		crudService.update(organization);

		return Arrays.asList(new String[] { "hello", "world" });
	}

}
