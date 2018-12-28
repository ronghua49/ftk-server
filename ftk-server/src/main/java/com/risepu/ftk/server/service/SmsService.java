package com.risepu.ftk.server.service;

import net.lc4ever.framework.remote.annotation.Remote;

/**
 * @author ronghaohua
 */
@Remote(path = "/sms")
public interface SmsService {


    String registerSendSms(String phone);

    String authSendSms(String phone,String orgName);

    boolean identify(String inCode, String createCode);

}
