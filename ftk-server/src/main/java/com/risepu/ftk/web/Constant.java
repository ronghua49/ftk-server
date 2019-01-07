package com.risepu.ftk.web;

/**
 * @author ronghaohua
 */
public class Constant {
    private static final String SESSION_VERIFICATION_CODE_IMG = "SESSION_VERIFICATION_CODE_IMG";
    private static final String SESSION_VERIFICATION_CODE_SMS = "SESSION_VERIFICATION_CODE_SMS";
    private static final String SESSION_CURR_USER = "SESSION_CURR_USER";
    private static final String SESSION_CHAIN_HASH = "SESSION_CHAIN_HASH";

    public static String getSessionCurrUser() {
        return SESSION_CURR_USER;
    }

    public static String getSessionVerificationCodeImg() {
        return SESSION_VERIFICATION_CODE_IMG;
    }

    public static String getSessionVerificationCodeSms() {
        return SESSION_VERIFICATION_CODE_SMS;
    }

    public static String getSessionChainHash() {
        return SESSION_CHAIN_HASH;
    }
}
