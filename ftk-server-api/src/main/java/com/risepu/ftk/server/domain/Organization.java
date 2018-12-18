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
 * 企业表
 *
 * @author q-wang
 */
@Entity
@Table(name = "FTK_ORGANIZATION")
public class Organization extends AuditableObject<String> {

    private static final long serialVersionUID = 1L;

    private String id;

    @Override
    @Id
    @Column(name = "ID", length = 10)
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 企业名称
     */
    private String name;

    @Column(name = "NAME", length = 255, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String phone;

    @Column(name = "PHONE", length = 11, nullable = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phnoe) {
        this.phone = phnoe;
    }

    private String password;

    @Column(name = "PASSWORD", length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private boolean state;

    @Column(name = "STATE", length = 1)
    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
