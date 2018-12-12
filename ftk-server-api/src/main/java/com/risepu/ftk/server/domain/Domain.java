/**
 *
 */
package com.risepu.ftk.server.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.AuditableObject;

/**
 * 模板数据定义.
 *
 * @author q-wang
 */
@Entity
@Table(name = "FTK_DOMAIN")
public class Domain extends AuditableObject<Long> {

	/** 主键 */
	private Long id;

	/** 模板数据名称 */
	private String name;

	/** 模板数据类型 */
	private String type;

	/** 描述 */
	private String description;
	
	/**是否删除*/
	private Integer isDelete;
	
	/**创建时间*/
	private Date create_time;

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

	@Column(name = "NAME", length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TYPE", length = 20)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "DESCRIPTION", length = 255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "IS_DELETE", length = 1)
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}
