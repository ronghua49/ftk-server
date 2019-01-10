package com.risepu.ftk.web.exception;    /*
 * @author  ronghaohua
 * @date 2018/12/25
 */

public class NotLoginException extends RuntimeException {

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
