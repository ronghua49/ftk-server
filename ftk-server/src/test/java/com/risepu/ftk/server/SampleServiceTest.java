/**
 *
 */
package com.risepu.ftk.server;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.TemplateDomain;
import com.risepu.ftk.server.domain.TemplateDomain.ID;
import com.risepu.ftk.server.service.DomainService;
import com.risepu.ftk.server.service.SampleService;

import net.lc4ever.framework.service.GenericCrudService;

/**
 * @author q-wang
 */
public class SampleServiceTest extends AbstractServerTestCase {

	@Autowired
	private SampleService sampleService;
	
	@Autowired
	private DomainService domainService;
	
	
	private GenericCrudService crudService;

	@Autowired
	public void setCrudService(GenericCrudService crudService) {
		this.crudService = crudService;
	}

	@Test
	public void testSample() throws Exception {
//		sampleService.sample1();
		Long i=(long) 121212;
		TemplateDomain templateDomain=new TemplateDomain();
		ID id=new ID();
		id.setDomainId(i);
		id.setTemplateId(i);
		templateDomain.setId(id);
		System.out.println(crudService.save(templateDomain).toString());
		//System.out.println(domainService.selectAll().size());
		
		
	}
}
