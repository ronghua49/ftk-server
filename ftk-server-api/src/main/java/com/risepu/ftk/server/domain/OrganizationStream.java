package com.risepu.ftk.server.domain;    /*
 * @author  Administrator
 * @date 2018/12/25
 * 企业发起认证流水表
 */

import net.lc4ever.framework.domain.AuditableObject;

import javax.persistence.*;

@Entity
@Table(name = "FTK_ORGANIZATION_STREAM")
public class OrganizationStream extends AuditableObject<Long> {

    /** 未审核 */
    public static final Integer UNCHECK_STATE = 0;
    /** 审核中 */
    public static final Integer CHECKING_STATE = 1;
    /** 审核通过 */
    public static final Integer CHECK_PASS_STATE = 2;
    /** 审核未通过 */
    public static final Integer CHECK_FAIL_STATE = 3;


    /** 自增id */
    private Long id;

    @Id
    @Column(name = "ID", precision = 19)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;

    }

    /** 企业的组织机构代码证号 */
   private String organization;

    @Column(name = "ORGANIZATION", length = 18, nullable = false)
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /** 企业名 */
    private String name;

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /** 审核状态   默认未审核 */
    private Integer state = UNCHECK_STATE;

    @Column(name = "STATE", length = 1)

    public Integer getState() {

        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    private String address;

    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String legalPerson;

    @Column(name = "LEGAL_PERSON", length = 50)
    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    private String licenseImgName;

    @Column(name = "LICENCE_IMG_NAME")
    public String getLicenseImgName() {
        return licenseImgName;
    }

    public void setLicenseImgName(String licenseImgName) {
        this.licenseImgName = licenseImgName;
    }

    private String remark;

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private String tel;

    @Column(name = "TEL", length = 50)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    private String applicationPhone;

    @Column(name = "APPLICATION_PHONE", length = 11)
    public String getApplicationPhone() {
        return applicationPhone;
    }

    public void setApplicationPhone(String applicationPhone) {
        this.applicationPhone = applicationPhone;
    }

    private String orgType;
    private String signSts;
    private String registedCapital;
    private String scope;
    private String registedDate;
    private String insuranceNum;
    private String staffSize;
    private String website ;

    /**  行业二级编号*/
    private String code;

    /** 行业一级编号 */
    private String dictCode;
    /** 渠道名称 */
    private String channalName;
    @Column(name = "ORG_TYPE", length = 20)
    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Column(name = "SIGN_STS", length = 20)
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

    @Column(name = "SCOPE",length = 1000)
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Column(name = "REG_DATE", length = 50)
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

    @Column(name = "WEBSITE")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "DICT_CODE")
    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }
    @Column(name = "CHANNAL_NAME",length = 50)
    public String getChannalName() {
        return channalName;
    }

    public void setChannalName(String channalName) {
        this.channalName = channalName;
    }
}
