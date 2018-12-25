package com.risepu.ftk.web.exception;    /*
 * @author  Administrator
 * @date 2018/12/25
 */

/**
 * 未登录异常
 */
public class NotLoginException  extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public NotLoginException() {
        super();
    }

    public NotLoginException(String message) {
        super(message);
    }

    public NotLoginException(Throwable cause) {
        super(cause);
    }
}
