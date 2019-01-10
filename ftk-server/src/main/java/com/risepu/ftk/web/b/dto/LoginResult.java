package com.risepu.ftk.web.b.dto;

import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationStream;
import com.risepu.ftk.server.domain.OrganizationUser;

/**
 * @author ronghaohua
 */
public class LoginResult {
	
	private int code; 
	
	private String message;
	/** 未认证企业 */
	private OrganizationUser organizationUser;
	/** 认证企业 */
	private OrganizationStream organizationStream;
	
	

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
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

	public OrganizationStream getOrganizationStream() {
		return organizationStream;
	}

	public void setOrganizationStream(OrganizationStream organizationStream) {
		this.organizationStream = organizationStream;
	}
}
