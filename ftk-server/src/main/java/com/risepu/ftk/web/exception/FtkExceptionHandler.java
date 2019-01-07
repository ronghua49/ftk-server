package com.risepu.ftk.web.exception;    /*
 * @author  Administrator
 * @date 2018/12/25
 */

import com.risepu.ftk.web.api.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ronghaohua
 */
@ControllerAdvice
public class FtkExceptionHandler {
    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public ResponseEntity<Response<String>> notFoundException() {
        return ResponseEntity.ok(Response.failed(-101, "您还未登录，请登录后操作"));
    }
}
