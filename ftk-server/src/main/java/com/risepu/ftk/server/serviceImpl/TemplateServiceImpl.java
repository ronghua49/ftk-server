package com.risepu.ftk.server.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.TemplateService;

import net.lc4ever.framework.service.GenericCrudService;

/**
 * @author L-heng
 */
@Service
public class TemplateServiceImpl implements TemplateService {
    private GenericCrudService crudService;

    @Autowired
    public void setCrudService(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    @Override
    public Long add(Template template) {
        // TODO Auto-generated method stub
        template.setState(0);
        return crudService.save(template);
    }

    @Override
    public void update(Template template) {
        crudService.update(template);
    }

    @Override
    public List<Template> getAllTemplate() {
        // TODO Auto-generated method stub
        return crudService.hql(Template.class, "from Template");
    }

    @Override
    public List<Template> getTemplates() {
        return crudService.hql(Template.class, "from Template where state = 0");
    }

    @Override
    public Template getTemplate(Long template) {
        // TODO Auto-generated method stub
        Template temp = crudService.uniqueResultHql(Template.class, "from Template where id = ?1 ", template);
        return temp;
    }

}
