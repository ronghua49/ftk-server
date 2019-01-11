package com.risepu.ftk.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.TimestampObject;

/**
 * 企业用户表
 *
 * @author q-wang 企业用户表
 */
@Entity
@Table(name = "FTK_ORGANIZATION_USER")
public class OrganizationUser extends TimestampObject<String> {

    public static  Integer ORG_USER_TYPE=0;

    /**
     * 手机号
     */
    private String id;

    private String password;

    /**
     * 根据企业的组织机构代码证查询 对应的企业
     */
    private String organizationId;

    /**
     * 企业用户类型
     */
    private Integer userType=ORG_USER_TYPE;


    /**
     * 邀请码
     */
    private String inviteCode;


    private String invalidInviteCode;

    @Override
    @Id
    @Column(name = "ID", length = 11)
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "PASSWORD", length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "ORGANIZATION_ID")
    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
    @Column(name = "USER_TYPE",length = 1)
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
    @Column(name = "INVITE_CODE",length = 10)
    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
    @Column(name = "INVALID_INVITE_CODE",length = 10)
    public String getInvalidInviteCode() {
        return invalidInviteCode;
    }

    public void setInvalidInviteCode(String invalidInviteCode) {
        this.invalidInviteCode = invalidInviteCode;
    }
}
