package com.risepu.ftk.server.domain;

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
	/** 等待授权*/
	public  static final int AUTH_STATE_NEW=0;
	/** 授权通过*/
	public  static final int AUTH_STATE_PASS=1;
	/** 拒绝授权*/
	public  static final int AUTH_STATE_REFUSE=2;
	


	
	
	/** 主键*/
	private Long  id;
	/** 用户身份证号*/
	private String personId;
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

	@Column(name="PERSON_ID",length=18)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
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
