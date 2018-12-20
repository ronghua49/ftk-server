package com.risepu.ftk.server.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@Column(name="PASSWORD",length=32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	@ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ORGANIZATION_ID")
	private Organization organization;

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	
	
	

}
