package com.risepu.ftk.web;    /*
 * @author  Administrator
 * @date 2018/12/21
 */

import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.dto.MesRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public interface SmsControllerApi {
    @ApiOperation(value = "发送短信", nickname = "login")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/get")
    @ResponseBody
    ResponseEntity<Response<String>> sendSms(@RequestBody MesRequest mesRequest, HttpServletRequest request);
}
