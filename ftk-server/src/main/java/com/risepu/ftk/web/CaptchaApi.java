package com.risepu.ftk.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risepu.ftk.web.api.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "cap")
public interface CaptchaApi {

    @ApiOperation(value = "获取图片验证码", nickname = "getCapcha")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/get")
    @ResponseBody
    ResponseEntity<Response<String>> captcha(HttpServletResponse response, HttpServletRequest request);


}
