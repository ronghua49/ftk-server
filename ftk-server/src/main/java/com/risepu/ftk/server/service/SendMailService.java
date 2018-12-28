package com.risepu.ftk.server.service;


/**
 * @author L-heng
 */
public interface SendMailService {
    void sendMail(String email, String filePath, String title) throws Exception;

}
