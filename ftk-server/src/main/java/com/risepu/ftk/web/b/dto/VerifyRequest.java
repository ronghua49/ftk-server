package com.risepu.ftk.web.b.dto;    /*
 * @author  Administrator
 * @date 2018/12/26
 */

public class VerifyRequest {
    private Long streamId;
    private String hash;
    private String authCode;
    private String cardNo;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Long getStreamId() {
        return streamId;
    }

    public void setStreamId(Long streamId) {
        this.streamId = streamId;
    }
}
