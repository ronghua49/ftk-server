package com.risepu.ftk.web.b.controller;

import java.util.Map;

import com.risepu.ftk.web.m.dto.EmailRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.risepu.ftk.web.api.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author L-heng
 */
@Api(value = "documentData")
public interface DocumentDataApi {

    /**
     * 生成文档
     *
     * @param map
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "生成文档", nickname = "add")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @PostMapping(path = "/add")
    @ResponseBody
    @CrossOrigin
    ResponseEntity<Response<String>> add(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 发送邮件
     *
     * @return 邮件发送状态
     * @throws Exception
     */
    @ApiOperation(value = "发送邮件", nickname = "sendEmail")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @PostMapping(path = "/sendEmail")
    @ResponseBody
    @CrossOrigin
    ResponseEntity<Response<String>> sendEmail(@RequestBody EmailRequest emailRequest, HttpServletRequest request);
}
