package com.risepu.ftk.server.service;

import net.lc4ever.framework.remote.annotation.Remote;

@Remote(path = "/person")
public interface PersonalUserService {

    String personReg(String phone, String code, String password);


    String personLoginUsePwd(String phone, String password);


    String personLoginUseCode(String phone, String code);

}
