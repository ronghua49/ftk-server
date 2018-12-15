/**
 *
 */
package com.risepu.ftk.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.AuditableObject;

/**
 * 
 * 个人用户表
 * @author q-wang
 */
@Entity
@Table(name = "FTK_ADMIN_USER")
public class AdminUser extends AuditableObject<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 用户名 */
	private String id;

	@Id
	@Column(name = "ID", length = 32)
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	/*密码*/
	private String password;
	
	@Column(name ="PASSWORD", length = 32)
	public String getPassword() {
		return password;
	}
	
	
	public void setPassword(String password) {
		this.password = password;
	}
	

}
