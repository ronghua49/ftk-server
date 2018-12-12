package com.risepu.ftk.web.b.dto;

import com.risepu.ftk.server.domain.OrganizationUser;

public class LoginResult {
	
	private String message;
	
	private OrganizationUser organizationUser;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public OrganizationUser getOrganizationUser() {
		return organizationUser;
	}

	public void setOrganizationUser(OrganizationUser organizationUser) {
		this.organizationUser = organizationUser;
	}
	
	

}
