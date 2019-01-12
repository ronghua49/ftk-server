package com.risepu.ftk.web.m.dto;

import java.util.Date;

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
    private String documentType;
    /**
     * 生成日期
     */
    private Date time;
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


    /**
     *渠道名称
     */
    private String channelName;

    public DocumentRequest() {
    }

    public DocumentRequest(String organizationName, String organizationCode, String type, String documentType, Date time, String number, String idCard, String chainHash,String channelName) {
        this.organizationName = organizationName;
        this.organizationCode = organizationCode;
        this.type = type;
        this.documentType = documentType;
        this.time = time;
        this.number = number;
        this.idCard = idCard;
        this.chainHash = chainHash;
        this.channelName = channelName;
    }

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

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
