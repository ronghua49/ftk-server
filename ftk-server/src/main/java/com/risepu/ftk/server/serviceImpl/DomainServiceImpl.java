package com.risepu.ftk.server.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.service.DomainService;

import net.lc4ever.framework.service.GenericCrudService;

/**
 * 
 * @author L-heng
 *
 */
@Service
public class DomainServiceImpl implements DomainService {
	private GenericCrudService crudService;

	@Autowired
	public void setCrudService(GenericCrudService crudService) {
		this.crudService = crudService;
	}

	@Override
	public Long add(String name, String type, String description) {
		// TODO Auto-generated method stub
		Domain domin = new Domain();
		domin.setName(name);
		domin.setType(type);
		domin.setIsDelete(0);
		domin.setDescription(description);
		domin.setCreate_time(new Date());
		return crudService.save(domin);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		Domain domin = crudService.get(Domain.class, id);
		crudService.delete(domin);
	}

	@Override
	public void update(String name, String type, String description) {
		// TODO Auto-generated method stub
		Domain domin = new Domain();
		domin.setName(name);
		domin.setType(type);
		domin.setDescription(description);
		crudService.update(domin);
	}

	@Override
	public Domain selectById(Long id) {
		// TODO Auto-generated method stub
		return crudService.get(Domain.class, id);
	}

	@Override
	public void updateState(Long id) {
		// TODO Auto-generated method stub
		Domain domin = crudService.get(Domain.class, id);
		domin.setIsDelete(1);
		crudService.update(domin);
	}

	@Override
	public List<Domain> selectAll() {
		// TODO Auto-generated method stub
		return crudService.hql(Domain.class, "from Domain");
	}

	@Override
	public List<Domain> selectByTemplate(Long templateId) {
		// TODO Auto-generated method stub
		List<Domain> list = crudService.hql(Domain.class,
				"from Domain where id in (select domainId from TemplateDomain where templateId = ? )",
				templateId);
		return list;
	}

}
