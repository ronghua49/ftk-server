package com.risepu.ftk.server.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.TemplateService;

import net.lc4ever.framework.service.GenericCrudService;

/**
 * 
 * @author L-heng
 *
 */
@Service
public class TemplateServiceImpl implements TemplateService {
	private GenericCrudService crudService;

	@Autowired
	public void setCrudService(GenericCrudService crudService) {
		this.crudService = crudService;
	}
	@Override
	public void add(String name, String description, String filePath) {
		// TODO Auto-generated method stub
		Template template=new Template();
		template.setName(name);
		template.setDescription(description);
		template.setFilePath(filePath);
		crudService.save(template);
	}
	@Override
	public List<Template> select() {
		// TODO Auto-generated method stub
		return crudService.hql(Template.class, "from Template");
	}

}
