package com.risepu.ftk.server;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.risepu.ftk.server.domain.TemplateDomain;
import com.risepu.ftk.server.domain.TemplateDomain.ID;

import net.lc4ever.framework.service.GenericCrudService;

/**
 * @author q-wang
 */
public class SampleServiceTest extends AbstractServerTestCase {

	@Autowired
	private GenericCrudService crudService;

	@Autowired
	public void setCrudService(GenericCrudService crudService) {
		this.crudService = crudService;
	}

	@Test
	public void testSample() throws Exception {
		Long i = (long) 121211;
		TemplateDomain templateDomain = new TemplateDomain();
		ID id = new ID();
		id.setDomainId(i);
		id.setTemplateId(i);
		templateDomain.setId(id);
		System.out.println(crudService.save(templateDomain).toString());
	}

}
