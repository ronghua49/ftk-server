package com.risepu.ftk.web.b.dto;

public class ChangePwdRequest {
	
	private String originalPwd;
	private String newPwd;
	private String rePwd;
	public String getOriginalPwd() {
		return originalPwd;
	}
	public void setOriginalPwd(String originalPwd) {
		this.originalPwd = originalPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getRePwd() {
		return rePwd;
	}
	public void setRePwd(String rePwd) {
		this.rePwd = rePwd;
	}
	
	

}
