package com.risepu.ftk.web.p.dto;

import com.risepu.ftk.server.domain.PersonalUser;

public class LoginResult {
	
	private String message;
	
	private PersonalUser personalUser;
	
	private String orgName;
	
	private Integer streamId;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PersonalUser getPersonalUser() {
		return personalUser;
	}

	public void setPersonalUser(PersonalUser personalUser) {
		this.personalUser = personalUser;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getStreamId() {
		return streamId;
	}

	public void setStreamId(Integer streamId) {
		this.streamId = streamId;
	}

	
	
	
	

}
