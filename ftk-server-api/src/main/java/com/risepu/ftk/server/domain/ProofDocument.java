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
 * @author q-wang
 */
@Entity
@Table(name = "FTK_PROOF_DOCUMENT")
public class ProofDocument extends AuditableObject<Long> {

	private Long id;

	@Id
	@Column(name = "ID", precision = 19)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	private Long template;

	@Column(name = "TEMPLATE", precision = 19)
	public Long getTemplate() {
		return template;
	}

	public void setTemplate(Long template) {
		this.template = template;
	}

	private String chainHash;

	@Column(name = "CHAIN_HASH", length = 128)
	public String getChainHash() {
		return chainHash;
	}

	public void setChainHash(String chainHash) {
		this.chainHash = chainHash;
	}

	private String organization;

	private String personalUser;

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getPersonalUser() {
		return personalUser;
	}

	public void setPersonalUser(String personalUser) {
		this.personalUser = personalUser;
	}

}
