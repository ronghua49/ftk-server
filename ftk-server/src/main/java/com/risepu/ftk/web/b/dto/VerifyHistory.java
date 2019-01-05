package com.risepu.ftk.web.b.dto;    /*
 * @author  ronghaohua
 * @date 2018/12/29
 */

import java.util.Date;

public class VerifyHistory {

    private String chainHash;

    private String number;

    private Date createTimestamp;

    private String idCard;

    public VerifyHistory() {
    }

    public VerifyHistory(String chainHash, String number, Date createTimestamp, String idCard) {
        this.chainHash = chainHash;
        this.number = number;
        this.createTimestamp = createTimestamp;
        this.idCard = idCard;
    }

    public String getChainHash() {
        return chainHash;
    }

    public void setChainHash(String chainHash) {
        this.chainHash = chainHash;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
