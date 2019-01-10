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
 *
 * @version 1.0
 * @ClassName: AuthorizationStream
 * @author:荣浩华
 * @date:2018年12月13日下午2:54:14
 */
@Entity
@Table(name = "FTK_AUTHORIZATION_STREAM")
public class AuthorizationStream extends AuditableObject<Long> {
    /**
     * 等待授权
     */
    public static final int AUTH_STATE_NEW = 0;
    /**
     * 授权通过
     */
    public static final int AUTH_STATE_PASS = 1;
    /**
     * 拒绝授权
     */
    public static final int AUTH_STATE_REFUSE = 2;
    /**
     * 单据验证成功
     */
    public static final int VERIFY_STATE_PASS = 3;
    /**
     * 单据验证失败
     */
    public static final int VERIFY_STATE_FAIL = 4;

    /**
     * 主键
     */
    private Long id;

    /**
     * 单据上的用户身份证号
     */
    private String personId;

    /**
     * 当前企业id
     */
    private String orgId;

    /**
     * 授权状态
     */
    private Integer authState;

    /**
     * 验证状态
     */
    private Integer verifyState;

    /**
     * 验证成功后的 当前单据 hash
     */
    private String chainHash;

    /**
     * 存放发送用户的授权码
     */
    private String authCode;

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

    @Column(name = "PERSON_ID", length = 18)
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Column(name = "ORG_ID", length = 18)
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Column(name = "CHAIN_HASH", length = 128)
    public String getChainHash() {
        return chainHash;
    }

    public void setChainHash(String chainHash) {
        this.chainHash = chainHash;
    }

    @Column(name = "AUTH_STATE", length = 1)
    public Integer getAuthState() {
        return authState;
    }

    public void setAuthState(Integer authState) {
        this.authState = authState;
    }

    @Column(name = "VERIFY_STATE", length = 1)
    public Integer getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(Integer verifyState) {
        this.verifyState = verifyState;
    }

    @Column(name = "AUTH_CODE", length = 128)
    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
