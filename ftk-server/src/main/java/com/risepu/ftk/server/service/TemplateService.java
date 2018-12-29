package com.risepu.ftk.server.service;

import java.util.List;

import com.risepu.ftk.server.domain.Template;

/**
 * @author L-heng
 */
public interface TemplateService {
    /**
     * 新增模板
     */
    Long add(Template template);

    /**
     * 更新模板
     */
    void update(Template template);

    /**
     * 查询所有模板
     */
    List<Template> getAllTemplate(String hql);

    /**
     * 查询已启用模板
     */
    List<Template> getTemplates();

    /**
     * 根据模板id查找模板
     */
    Template getTemplate(Long template);

    /**
     * @param firstIndex
     * @param pageSize
     * @param hql
     * @return
     */
    List getAnyTemplate(Integer firstIndex, Integer pageSize, String hql);
}
