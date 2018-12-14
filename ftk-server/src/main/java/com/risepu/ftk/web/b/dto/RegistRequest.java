package com.risepu.ftk.web.b.dto;


public class RegistRequest {
	
	private String mobile;
	
	private String password;
	
	private String msmCode;
	
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

	public String getMsmCode() {
		return msmCode;
	}

	public void setMsmCode(String msmCode) {
		this.msmCode = msmCode;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	@Override
	public String toString() {
		return "RegistRequest [mobile=" + mobile + ", password=" + password + ", msmCode=" + msmCode + ", imgCode="
				+ imgCode + "]";
	}
	
	
	

}
