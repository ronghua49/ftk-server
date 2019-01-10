package com.risepu.ftk.server.domain;

import javax.persistence.*;

import net.lc4ever.framework.domain.TimestampObject;

import java.io.Serializable;

/**
 * @author q-wang
 */
@Entity
@Table(name = "FTK_PERSONAL_USER")
public class PersonalUser extends TimestampObject<PersonalUser.ID> {

    @Embeddable
    public static class ID implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 个人用户唯一标识: 当前存储数据为身份证号码
         */
        private String id;

        /**
         * 手机号码
         */
        private String mobile;




        @Column(name = "ID", length = 128)
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Column(name = "MOBILE", length = 32)
        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ID id1 = (ID) o;

            if (!id.equals(id1.id)) {
                return false;
            }
            return mobile.equals(id1.mobile);
        }
    }

    private PersonalUser.ID id;

    @EmbeddedId
    @Override
    public PersonalUser.ID getId() {
        return id;
    }
    @Override
    public void setId(PersonalUser.ID id) {
        this.id = id;
    }

    public static  Integer PERSONAL_USER_TYPE=1;

    private String password;

    private String userName;

    /**
     * 企业用户类型
     */
    private Integer userType=PERSONAL_USER_TYPE;



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
