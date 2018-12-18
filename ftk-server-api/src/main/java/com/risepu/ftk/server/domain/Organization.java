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
 *
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

	private String phone;

	@Column(name = "PHONE", length = 11, nullable = false)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phnoe) {
		this.phone = phnoe;
	}

	private String password;

	@Column(name = "PASSWORD", length = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/** 发起认证的企业用户手机号 */
	private String id;

	@Override
	@Id
	@Column(name = "ID", length = 11)
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

	private String idCardImgName;

	@Column(name = "IDCARD_IMG_NAME", length = 50)
	public String getIdCardImgName() {
		return idCardImgName;
	}

	public void setIdCardImgName(String idCardImgName) {
		this.idCardImgName = idCardImgName;
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
