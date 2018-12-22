package com.risepu.ftk.web.b.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.web.api.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author L-heng
 */
@Api(value = "template")
public interface CompanyTemplateApi {

    /**
     * 显示已启用模板
     *
     * @return 模板JavaBean集合
     */
    @ApiOperation(value = "显示已启用模板", nickname = "getTemplates")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @PostMapping(path = "/getTemplates")
    @ResponseBody
    ResponseEntity<Response<List<String>>> getTemplates();

    /**
     * 查找模板对应的所有数据
     *
     * @param templateId
     * @return 模板数据
     */
    @ApiOperation(value = "查找模板对应的所有数据", nickname = "getAllTemplateData")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @PostMapping(path = "/getAllTemplateData")
    @ResponseBody
    ResponseEntity<Response<List<Domain>>> getAllTemplateData(Long templateId);
}
