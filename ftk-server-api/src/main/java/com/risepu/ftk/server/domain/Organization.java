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

    /**
     * 企业组织机构代码证
     */
    private String id;

    /**
     * 企业名
     */
    private String name;

    private String address;

    private String legalPerson;

    private Long defaultTemId;

    private String licenseImgName;

    private String remark;

    private String tel;

    private String orgType;

    private String signSts;

    private String registedCapital;

    private String scope;

    private String registedDate;

    private String insuranceNum;

    private String staffSize;

    private String website;

    @Override
    @Id
    @Column(name = "ORGANIZATION", length = 18)
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "NAME", length = 255, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ADDRESS", length = 31)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "LEGAL_PERSON", length = 10)
    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    @Column(name = "DEFAULT_TEP_ID", length = 19)
    public Long getDefaultTemId() {
        return defaultTemId;
    }

    public void setDefaultTemId(Long defaultTemId) {
        this.defaultTemId = defaultTemId;
    }

    @Column(name = "LICENCE_IMG_NAME")
    public String getLicenseImgName() {
        return licenseImgName;
    }

    public void setLicenseImgName(String licenseImgName) {
        this.licenseImgName = licenseImgName;
    }

    @Column(name = "REMARK", length = 225)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "TEL", length = 15)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Column(name = "ORG_TYPE", length = 20)
    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Column(name = "SIGN_STS", length = 10)
    public String getSignSts() {
        return signSts;
    }

    public void setSignSts(String signSts) {
        this.signSts = signSts;
    }

    @Column(name = "REG_CAPITAL", length = 20)
    public String getRegistedCapital() {
        return registedCapital;
    }

    public void setRegistedCapital(String registedCapital) {
        this.registedCapital = registedCapital;
    }

    @Column(name = "SCOPE", length = 20)
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Column(name = "REG_DATE", length = 10)
    public String getRegistedDate() {
        return registedDate;
    }

    public void setRegistedDate(String registedDate) {
        this.registedDate = registedDate;
    }

    @Column(name = "INSURANCE_NUM", length = 20)
    public String getInsuranceNum() {
        return insuranceNum;
    }

    public void setInsuranceNum(String insuranceNum) {
        this.insuranceNum = insuranceNum;
    }

    @Column(name = "STAFF_SIZE", length = 20)
    public String getStaffSize() {
        return staffSize;
    }

    public void setStaffSize(String staffSize) {
        this.staffSize = staffSize;
    }

    @Column(name = "WEBSITE", length = 20)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
