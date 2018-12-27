package com.risepu.ftk.server.service;

import net.lc4ever.framework.remote.annotation.Remote;

@Remote(path = "/sms")
public interface SmsService {


    String sendCode(String phone);


    boolean identify(String inCode, String createCode);

}
