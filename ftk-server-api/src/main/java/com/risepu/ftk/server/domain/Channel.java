package com.risepu.ftk.server.domain;    /*
 * @author  Administrator
 * @date 2019/1/11
 */

import net.lc4ever.framework.domain.TimestampObject;

import javax.persistence.*;


@Entity
@Table(name = "FTK_CHANNEL")
public class Channel extends TimestampObject<Long> {

    private Long id;

    private String inviteCode;

    private String channelCode;

    private String channelName;

    private String  contactPerson;

    private String tel;

    private  String remark;

    @Override
    @Id
    @Column(name = "ID", precision = 19)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;

    }
    @Column(name = "INVITE_CODE",length = 10)
    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    @Column(name = "CHANNEL_CODE",length = 100)
    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    @Column(name = "CHANNEL_NAME",length = 100)
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Column(name = "CONTACT_PERSON",length = 20)
    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Column(name = "TEL",length = 30)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
