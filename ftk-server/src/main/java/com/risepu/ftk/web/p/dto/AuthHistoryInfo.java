package com.risepu.ftk.web.p.dto;

import java.util.Date;

public class AuthHistoryInfo {

	private String name;
	
	private Date modifyTimestamp;
	
	private String address;
	
	private String tel;
	
	private Integer state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getModifyTimestamp() {
		return modifyTimestamp;
	}

	public void setModifyTimestamp(Date modifyTimestamp) {
		this.modifyTimestamp = modifyTimestamp;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "AuthHistoryInfo{" +
				"name='" + name + '\'' +
				", modifyTimestamp=" + modifyTimestamp +
				", address='" + address + '\'' +
				", tel='" + tel + '\'' +
				", state=" + state +
				'}';
	}
}
