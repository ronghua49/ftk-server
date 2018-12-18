package com.risepu.ftk.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.TimestampObject;

/**
 * 企业用户表
 *
 * @author q-wang 企业用户表
 */
@Entity
@Table(name = "FTK_ORGANIZATION_USER")
public class OrganizationUser extends TimestampObject<String> {

	private static final long serialVersionUID = 1L;

	/* 企业名称 */
	private String organization;

	@Column(name = "ORGANIZATION", length = 10)
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
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

	private String phoneNo;

	@Column(name = "PHONE_NO", length = 11)
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	private String imgPath;

	@Column(name = "IMG_PATH", length = 225)
	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/** 手机号 */
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

	private String password;

	@Column(name = "PASSWORD", length = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
