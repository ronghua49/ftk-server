package com.risepu.ftk.web.p.dto;

import com.risepu.ftk.server.domain.PersonalUser;

public class LoginResult {
	
	private String message;
	
	private PersonalUser personalUser;
	
	
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

	public PersonalUser getPersonalUser() {
		return personalUser;
	}

	public void setPersonalUser(PersonalUser personalUser) {
		this.personalUser = personalUser;
	}

	
	
	

}
