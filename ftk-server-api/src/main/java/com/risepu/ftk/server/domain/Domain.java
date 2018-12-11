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
 * 模板数据定义.
 *
 * @author q-wang
 */
@Entity
@Table(name = "FTK_DOMAIN")
public class Domain extends AuditableObject<Long> {

	private Long id;

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

}
