package com.risepu.ftk.web.m.dto;

public class DocumentRequest {
    /**
     * 企业名称
     */
    private String organizationName;
    /**
     * 组织机构代码
     */
    private String organizationCode;
    /**
     * 行业类别
     */
    private String type;
    /**
     * 单据类型
     */
    private String docunmentCode;
    /**
     * 生成日期
     */
    private String time;
    /**
     * 单据编码
     */
    private String number;

    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 区块链编码
     */
    private String chainHash;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocunmentCode() {
        return docunmentCode;
    }

    public void setDocunmentCode(String docunmentCode) {
        this.docunmentCode = docunmentCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getChainHash() {
        return chainHash;
    }

    public void setChainHash(String chainHash) {
        this.chainHash = chainHash;
    }
}
