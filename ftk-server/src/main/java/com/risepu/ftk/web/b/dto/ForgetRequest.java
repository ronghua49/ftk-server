package com.risepu.ftk.web.b.dto;

/**
 * @author ronghaohua
 */
public class ForgetRequest {
	
	private String mobileOrName;
	
	private String smsCode;
	
	private String password;
	
	private String rePassword;

	public String getMobileOrName() {
		return mobileOrName;
	}

	public void setMobileOrName(String mobileOrName) {
		this.mobileOrName = mobileOrName;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	
	

}
