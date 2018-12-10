/**
 * 
 */
package com.risepu.ftk.server.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.AuditableObject;

/**
 * @author q-wang
 */
@Table(name = "FTK_ORGANIZATION")
public class Organization extends AuditableObject<String> {

	private String id;

	@Id
	@Column(name = "ID", length = 10)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String name;

	@Column(name = "NAME", length = 255, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
