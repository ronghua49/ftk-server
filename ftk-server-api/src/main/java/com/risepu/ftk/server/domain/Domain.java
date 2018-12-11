/**
 *
 */
package com.risepu.ftk.server.domain;

import javax.persistence.Entity;
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

	private Long id;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
