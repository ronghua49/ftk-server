package com.risepu.ftk.server.service;

import java.util.Map;

import net.lc4ever.framework.remote.annotation.Remote;

/**
 * @author ronghaohua
 */
@Remote(path = "/sms")
public interface SmsService {
    static final String regTemplateCode = "SMS_153991308";
    static final String authTemplateCode = "SMS_153996284";

    String sendCode(String phone, String templateCode, Map<String, String> params);

    boolean identify(String inCode, String createCode);
}
