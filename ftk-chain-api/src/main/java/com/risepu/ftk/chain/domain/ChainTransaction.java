/**
 *
 */
package com.risepu.ftk.chain.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.TimestampObject;

/**
 * @author q-wang
 */
@Entity
@Table(name = "FTK_CHAIN_TX")
public class ChainTransaction extends TimestampObject<String> {

	private String id;

	@Id
	@Column(name = "ID", length = 128)
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	private Long document;

	private Long template;

	private String organization;

	private String personal;

	private String data;
}
