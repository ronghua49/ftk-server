package com.risepu.ftk.server.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risepu.ftk.server.domain.TemplateDomain;
import com.risepu.ftk.server.domain.TemplateDomain.ID;
import com.risepu.ftk.server.service.TemplateDomainService;

import net.lc4ever.framework.service.GenericCrudService;

/**
 * @author L-heng
 */
@Service
public class TemplateDomainServiceImpl implements TemplateDomainService {

	private GenericCrudService crudService;

	@Autowired
	public void setCrudService(GenericCrudService crudService) {
		this.crudService = crudService;
	}

	@Override
	public void add(Long templateId, Long domainId) {
		// TODO Auto-generated method stub
		TemplateDomain templateDomain = new TemplateDomain();
		ID id = new ID();
		id.setDomainId(domainId);
		id.setTemplateId(templateId);
		templateDomain.setId(id);
		crudService.saveOrUpdate(templateDomain);
	}

	@Override
	public void delete(Long templateId, Long domainId) {
		// TODO Auto-generated method stub
		TemplateDomain templateDomain = new TemplateDomain();
		ID id = new ID();
		id.setDomainId(domainId);
		id.setTemplateId(templateId);
		templateDomain.setId(id);
		crudService.delete(templateDomain);
	}

}
