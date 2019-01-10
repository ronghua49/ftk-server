package com.risepu.ftk.server.domain;

import net.lc4ever.framework.domain.TimestampObject;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ronghaohua
 */

public class RegisterUserReport {
    private  String userName;

    private Integer userType;

    private Date createTimestamp;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }
}
