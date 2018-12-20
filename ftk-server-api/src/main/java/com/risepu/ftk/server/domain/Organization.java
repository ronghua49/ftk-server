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
	/** 未审核 */
	public static final Integer UNCHECK_STATE = 0;
	/** 审核中 */
	public static final Integer CHECKING_STATE = 1;
	/** 审核通过 */
	public static final Integer CHECK_PASS_STATE = 2;
	/** 审核未通过 */
	public static final Integer CHECK_FAIL_STATE = 3;
	
	
	
	/** 企业组织机构代码证 */
	private String id;
	
	@Override
	@Id
	@Column(name = "ORGANIZATION", length = 11)
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}

	/** 企业名 */
	private String name;

	@Column(name = "NAME", length = 255, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	/** 审核状态   默认未审核 */
	private Integer state = UNCHECK_STATE;

	@Column(name = "STATE", length = 1)

	public Integer getState() {

		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	private String address;

	@Column(name = "ADDRESS", length = 31)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private String legalPerson;

	@Column(name = "LEGAL_PERSON", length = 10)
	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	private Long defaultTemId;

	@Column(name = "DEFAULT_TEP_ID", length = 19)
	public Long getDefaultTemId() {
		return defaultTemId;
	}

	public void setDefaultTemId(Long defaultTemId) {
		this.defaultTemId = defaultTemId;
	}

	private String licenseImgName;

	@Column(name = "LICENCE_IMG_NAME")
	public String getLicenseImgName() {
		return licenseImgName;
	}

	public void setLicenseImgName(String licenseImgName) {
		this.licenseImgName = licenseImgName;
	}

	private String remark;

	@Column(name = "REMARK", length = 225)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String tel;

	@Column(name = "TEL", length = 15)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}


}
