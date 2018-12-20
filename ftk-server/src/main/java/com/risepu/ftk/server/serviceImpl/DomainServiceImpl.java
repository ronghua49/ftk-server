package com.risepu.ftk.server.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.service.DomainService;

import net.lc4ever.framework.service.GenericCrudService;

/**
 * @author L-heng
 */
@Service
public class DomainServiceImpl implements DomainService {
    private GenericCrudService crudService;

    @Autowired
    public void setCrudService(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    @Override
    public Long add(Domain domain) {
        // TODO Auto-generated method stub
        domain.setIsDelete(0);
        return crudService.save(domain);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        Domain domain = crudService.get(Domain.class, id);
        crudService.delete(domain);
    }

    @Override
    public void update(Domain domain) {
        // TODO Auto-generated method stub
        domain.setIsDelete(0);
        crudService.update(domain);
    }

    @Override
    public Domain selectById(Long id) {
        // TODO Auto-generated method stub
        return crudService.get(Domain.class, id);
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
                "from Domain d where d.id in (select t.id.domainId from TemplateDomain t where t.id.templateId = ?1 )",
                templateId);
        return list;
    }

    @Override
    public void updateState(Long id) {
        // TODO Auto-generated method stub
        Domain domin = crudService.get(Domain.class, id);
        domin.setIsDelete(1);
        crudService.update(domin);
    }

    @Override
    public Domain selectByCode(String code) {
        // TODO Auto-generated method stub
        Domain domain = crudService.uniqueResultHql(Domain.class, "from Domain where code = ?1", code);
        return domain;
    }
}
