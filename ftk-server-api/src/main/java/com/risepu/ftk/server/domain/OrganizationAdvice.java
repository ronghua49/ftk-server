package com.risepu.ftk.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.TimestampObject;

@Entity
@Table(name = "FTK_ORGANIZATION_ADVICE")
public class OrganizationAdvice extends TimestampObject<Long> {

	/** 自增主键 */
	private Long id;

	/** 企业名称 */
	private String organizationName;
	/** 联系电话 */
	private String contactTel;
	/** 意见内容 */
	private String content;
	/** 当前企业id（手机号） */
	private String orgId;

	@Id
	@Column(name = "ID", precision = 19)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ORGANIZATION_NAME", length = 255, nullable = false)
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@Column(name = "CONTACT_TEL", length = 15, nullable = false)
	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	@Column(name = "CONTENT", length = 255, nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "ORG_ID", length = 11)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
