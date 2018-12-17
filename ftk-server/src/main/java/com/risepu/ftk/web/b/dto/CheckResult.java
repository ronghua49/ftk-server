package com.risepu.ftk.web.b.dto;

import com.risepu.ftk.server.domain.Organization;

public class CheckResult {

	private Integer checkNum;
	
	private Organization organization;

	public Integer getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(Integer checkNum) {
		this.checkNum = checkNum;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	
	
	
}
