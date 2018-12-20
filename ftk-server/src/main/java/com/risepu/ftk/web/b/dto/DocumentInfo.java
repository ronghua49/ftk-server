package com.risepu.ftk.web.b.dto;
/**
 * 企业验证历史 
 * @ClassName: VerifyHistory
 * @author:荣浩华
 * @date:2018年12月18日上午11:06:39
 * @version 1.0
 */
public class DocumentInfo {
	
	private String chainHash;
	
	private String userName;
	
	private String date;
	
	private String idCard;
	
	private String mobile;

	public String getChainHash() {
		return chainHash;
	}

	public void setChainHash(String chainHash) {
		this.chainHash = chainHash;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	

}
