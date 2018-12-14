package com.risepu.ftk.server.service;

import java.util.List;

import com.risepu.ftk.server.domain.Domain;

/**
 * 
 * @author L-heng
 *
 */
public interface DomainService {
	/** 新增模板数据 */
	public Long add(String name, String type, String description);

	/** 物理删除模板数据 */
	public void deleteById(Long id);

	/** 逻辑删除模板数据 */
	public void updateState(Long id);

	/** 修改模板数据 */
	public void update(String name, String type, String description);

	/** 根据id查找模板数据 */
	public Domain selectById(Long id);

	/** 根据模板id查询符合条件的所有模板数据 */
	public List<Domain> selectByTemplate(Long templateId);

	/** 查找所有模板数据 */
	public List<Domain> selectAll();
}
