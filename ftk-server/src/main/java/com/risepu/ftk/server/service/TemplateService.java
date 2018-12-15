package com.risepu.ftk.server.service;

import java.util.List;

import com.risepu.ftk.server.domain.Template;

/**
 * 
 * @author L-heng
 *
 */
public interface TemplateService {
	/** 新增模板 */
	public Long add(Template template);

	/** 查询模板 */
	public List<Template> select();

	/** 根据模板id得到模板路径 */
	public Template getTemplate(Long template);

}
