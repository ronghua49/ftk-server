package com.risepu.ftk.web.exception;    /*
 * @author  Administrator
 * @date 2018/12/25
 */

import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.ForgetRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class NotLoginExceptionHandler {

    @ExceptionHandler({NotLoginException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public  ResponseEntity<Response<String>>  notFoundException(){
        return ResponseEntity.ok(Response.failed(-101,"您还未登录，请登录后操作"));
    }

}
