package com.risepu.ftk.server.domain;

import net.lc4ever.framework.domain.TimestampObject;

import javax.persistence.*;

/**
 * 邮箱流水
 *
 * @author L-heng
 */
@Entity
@Table(name = "FTK_EMAIL_TRANSACTION")
public class EmailTransaction extends TimestampObject<Long> {
    private Long id;

    /**
     * 企业信息id
     */
    private String organization;

    private String organizationUser;

    private String personalUser;

    private String email;

    /**
     * 单号
     */
    private String number;

    @Id
    @Column(name = "ID", precision = 19)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORGANIZATION")
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Column(name = "ORGANIZATION_USER")
    public String getOrganizationUser() {
        return organizationUser;
    }

    public void setOrganizationUser(String organizationUser) {
        this.organizationUser = organizationUser;
    }

    @Column(name = "PERSONAL_USER")
    public String getPersonalUser() {
        return personalUser;
    }

    public void setPersonalUser(String personalUser) {
        this.personalUser = personalUser;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "NUMBER")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
