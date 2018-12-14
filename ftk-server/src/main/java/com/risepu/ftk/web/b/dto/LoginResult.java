package com.risepu.ftk.web.b.dto;

import com.risepu.ftk.server.domain.OrganizationUser;

public class LoginResult {
	
	private String message;
	
	private OrganizationUser organizationUser;
	
	
	private boolean isSuccess;
	
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

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
