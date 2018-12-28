package com.risepu.ftk.web.p.dto;


/**
 * @author ronghaohua
 */
public class RegistRequest {
	/** 企业id*/
	private String orgId;
	/** 用户手机号*/
	private String userMobile;
	
	private String idCard;
	
	private String userName;
	
	
	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
		

	

	
	
	

}
