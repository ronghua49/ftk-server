/**
 *
 */
package com.risepu.ftk.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.AuditableObject;

/**
 * 企业表
 * @author q-wang
 */
@Entity
@Table(name = "FTK_ORGANIZATION")
public class Organization extends AuditableObject<String> {

	private String id;

	@Override
	@Id
	@Column(name = "ID", length = 10)
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	/**企业名称*/
	private String name;

	@Column(name = "NAME", length = 255, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
