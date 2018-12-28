package com.risepu.ftk.web.b.dto;


/**
 * @author ronghaohua
 */
public class RegistRequest {
	
	private String mobile;
	
	private String password;
	
	private String smsCode;
	
	private String imgCode;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	@Override
	public String toString() {
		return "RegistRequest [mobile=" + mobile + ", password=" + password + ", smsCode=" + smsCode + ", imgCode="
				+ imgCode + "]";
	}
	
	
	

}
