package com.risepu.ftk.web.p.dto;

import com.risepu.ftk.server.domain.PersonalUser;

import java.util.Map;

public class LoginResult {
	
	private int code;
	
	private String message;
	
	private PersonalUser personalUser;
	
	private Map<Long,String> map;

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

	public Map<Long, String> getMap() {
		return map;
	}

	public void setMap(Map<Long, String> map) {
		this.map = map;
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	
	
	
	
	

}
