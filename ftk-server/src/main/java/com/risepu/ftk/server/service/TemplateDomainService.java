package com.risepu.ftk.server.service;

import com.risepu.ftk.server.domain.TemplateDomain.ID;

/**
 * @author L-heng
 */
public interface TemplateDomainService {
    /**
     * 新增模板与数据的关联
     */
    ID add(Long templateId, Long domainId);

    /**
     * 删除模板与数据间的关联
     */
    void delete(Long templateId, Long domainId);

}
