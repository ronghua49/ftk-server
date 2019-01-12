package com.risepu.ftk.web.b.controller;

import java.util.List;

import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.web.m.dto.IdRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.web.api.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletRequest;

/**
 * @author L-heng
 */
@Api(value = "template")
public interface CompanyTemplateApi {


    @ApiOperation(value = "显示已启用模板", nickname = "getTemplates")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @GetMapping(path = "/getTemplates")
    @ResponseBody
    ResponseEntity<Response<List<Template>>> getTemplates(String defaultState, HttpServletRequest request);


    @ApiOperation(value = "判断是否有默认模板", nickname = "getTemplateState")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @GetMapping(path = "/getTemplateState")
    @ResponseBody
    ResponseEntity<Response<String>> getTemplateState(HttpServletRequest request);


    @ApiOperation(value = "查找模板对应的所有数据", nickname = "getAllTemplateData")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @PostMapping(path = "/getAllTemplateData")
    @ResponseBody
    ResponseEntity<Response<List<Domain>>> getAllTemplateData(@RequestBody IdRequest templateId);
}
