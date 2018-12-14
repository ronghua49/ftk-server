package com.risepu.ftk.server.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.AuditableObject;
/**
 * 授权流水
 * @ClassName: AuthorizationStream
 * @author:荣浩华
 * @date:2018年12月13日下午2:54:14
 * @version 1.0
 */
@Entity
@Table(name = "FTK_AUTHORIZATION_STREAM")
public class AuthorizationStream extends AuditableObject<Long>{
	
	/** 授权通过*/
	public  static final int AUTH_STATE_PASS=0;
	/** 拒绝授权*/
	public  static final int AUTH_STATE_REFUSE=1;
	


	
	
	/** 主键*/
	private Long  id;
	/** 用户id*/
	private String userId;
	/** 企业id*/
	private String orgId;
	/** 授权状态*/
	private Integer state;
	
	@Id
	@Column(name = "ID", precision = 19)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	@Column(name="USER_ID",length=32)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name="ORG_ID",length=11)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Column(name="STATE",length=1)
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	
	
	
	
	
	
	

}
