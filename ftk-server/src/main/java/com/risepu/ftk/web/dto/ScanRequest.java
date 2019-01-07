package com.risepu.ftk.web.dto;

/**
 * @author ronghaohua
 */
public class ScanRequest {
    /**
     * 用户身份证
     */
    private String idCard;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 企业id
     */
    private String orgId;

    /**
     * 发送的授权状态 ：0 授权   1拒绝
     */
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
