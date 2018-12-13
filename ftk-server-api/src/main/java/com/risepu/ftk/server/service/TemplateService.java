package com.risepu.ftk.server.service;

import java.util.List;

import com.risepu.ftk.server.domain.Template;


public interface TemplateService {
	/** 新增模板 */
	public void add(String name, String description, String filePath);

	/** 查询模板 */
	public List<Template> select();

}
