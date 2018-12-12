/**
 *
 */
package com.risepu.ftk.web.b.dto;

import java.io.Serializable;

/**
 * @author q-wang
 */
public class RegistResult implements Serializable { // Data Transfer Object

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
