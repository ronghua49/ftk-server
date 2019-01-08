package com.risepu.ftk.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.TimestampObject;

/**
 * @author q-wang
 */
@Entity
@Table(name = "FTK_PERSONAL_USER")
public class PersonalUser extends TimestampObject<String> {

    public static  Integer PERSONAL_USER_TYPE=1;

    /**
     * 个人用户唯一标识: 当前存储数据为身份证号码
     */
    private String id;

    /**
     * 手机号码
     */
    private String mobile;

    private String password;

    private String userName;

    /**
     * 企业用户类型
     */
    private Integer userType=PERSONAL_USER_TYPE;

    @Id
    @Column(name = "ID", length = 128)
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "MOBILE", length = 32)
    @Id
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "PASSWORD", length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "USERNAME", length = 10)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "USER_TYPE",length = 1)
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

}
