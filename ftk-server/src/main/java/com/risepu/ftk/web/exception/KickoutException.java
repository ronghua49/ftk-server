package com.risepu.ftk.web.exception;    /*
 * @author  Administrator
 * @date 2019/1/12
 */

public class KickoutException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public KickoutException() {
        super();
    }

    public KickoutException(String message) {
        super(message);
    }

    public KickoutException(Throwable cause) {
        super(cause);
    }
}
