package com.risepu.ftk.web.b.controller;

import java.util.List;
import java.util.Map;

import com.risepu.ftk.server.domain.ProofDocument;
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
     * 生成文档(真)
     *
     * @param map
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "生成文档(真)", nickname = "addProofDocument")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @PostMapping(path = "/addProofDocument")
    @ResponseBody
    @CrossOrigin
    ResponseEntity<Response<ProofDocument>> addProofDocument(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 生成文档（假）
     *
     * @param map
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "生成文档（假）", nickname = "add")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = ProofDocument.class)})
    @PostMapping(path = "/add")
    @ResponseBody
    @CrossOrigin
    ResponseEntity<Response<ProofDocument>> add(@RequestBody Map<String, String> map, HttpServletRequest request) throws Exception;

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


    @ApiOperation(value = "邮件流水", nickname = "getEmails")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @GetMapping(path = "/getEmails")
    @ResponseBody
    @CrossOrigin
    ResponseEntity<Response<List>> getEmails(HttpServletRequest request);
}
