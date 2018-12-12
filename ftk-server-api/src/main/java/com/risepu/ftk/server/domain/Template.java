/**
 *
 */
package com.risepu.ftk.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.AuditableObject;

/**
 * 
 * 模板
 * 
 * @author q-wang
 */
@Entity
@Table(name = "FTK_TEMPLATE")
public class Template extends AuditableObject<Long> {

	/** 模板主键 */
	private Long id;

	/** 模板名称 */
	private String name;

	/** 模板描述 */
	private String description;

	/** 模板路径 */
	private String filePath;

	@Id
	@Column(name = "ID", precision = 19)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 32)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION", length = 512)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "FILE_PATH", length = 128)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
