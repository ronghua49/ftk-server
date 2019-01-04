package com.risepu.ftk.server.domain;

import net.lc4ever.framework.domain.TimestampObject;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ronghaohua
 */
@Entity
@Table(name = "FTK_REGISTER_REPORT")
public class RegisterUserReport extends TimestampObject<Long> {

    /**
     * 主键序列
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户类型
     */
    private Integer userType;


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

    @Column(name = "USER_NAME",length = 11)
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
