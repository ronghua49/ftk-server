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
     * 查询模板
     */
    List<Template> select();

    /**
     * 根据模板id查找模板
     */
    Template getTemplate(Long template);

}
