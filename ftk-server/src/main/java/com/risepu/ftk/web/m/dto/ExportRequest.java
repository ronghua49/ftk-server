package com.risepu.ftk.web.m.dto;    /*
 * @author  Administrator
 * @date 2019/1/14
 */

import java.util.List;

public class ExportRequest {

    private  String orgName;

    private  String type;

    private String createTimestamp;

    private  String number;

    private  String channelName;

    private List<String> hashs;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(String createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<String> getHashs() {
        return hashs;
    }

    public void setHashs(List<String> hashs) {
        this.hashs = hashs;
    }
}
