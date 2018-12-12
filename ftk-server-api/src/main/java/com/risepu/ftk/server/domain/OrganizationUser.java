/**
 *
 */
package com.risepu.ftk.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.TimestampObject;

/**
 * @author q-wang
 */
@Entity
@Table(name = "FTK_ORGANIZATION_USER")
public class OrganizationUser extends TimestampObject<String> {
	/*手机号*/
	private String id;

	@Override
	@Id
	@Column(name = "ID", length = 31)
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	
	private String password;
	
	@Column(name="PASSWORD",length=32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

}
