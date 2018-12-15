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
@Table(name ="FTK_ORGANIZATION")
public class Organization extends AuditableObject<String> {
	/** 未审核 */
	public static final Integer UNCHECK_STATE=0;
	/** 审核中 */
	public static final Integer CHECKING_STATE=1;
	/** 审核通过 */
	public static final Integer CHECK_PASS_STATE=2;
	/** 审核未通过 */
	public static final Integer CHECK_FAIL_STATE=3;
	

	/** 发起认证的企业用户手机号 */
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

	/** 企业名 */
	private String name;

	@Column(name = "NAME", length = 255, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** 组织机构代码 */
	private String organization;

	@Column(name = "ORGANIZATION", length = 10)
	public String getOrganization() {
		return organization;
	}
	
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	/** 审核状态   默认未审核 */
	private Integer state=UNCHECK_STATE;

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

	
	private String idCardImgPath;
	
	
	@Column(name="IDCARD_IMG_PATH",length=50)
	public String getIdCardImgPath() {
		return idCardImgPath;
	}

	public void setIdCardImgPath(String idCardImgPath) {
		this.idCardImgPath = idCardImgPath;
	}

	private String licenseImgPath;
	
	@Column(name="LICENCE_IMG_PATH")
	public String getLicenseImgPath() {
		return licenseImgPath;
	}

	public void setLicenseImgPath(String licenseImgPath) {
		this.licenseImgPath = licenseImgPath;
	}

	private String remark;

	@Column(name="REMARK",length=225)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	private String tel;
	
	
	@Column(name="TEL",length=11)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	
	
	

}
