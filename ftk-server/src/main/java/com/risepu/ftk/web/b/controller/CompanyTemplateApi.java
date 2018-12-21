package com.risepu.ftk.web.b.controller;

import java.util.List;

import com.risepu.ftk.utils.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Template;
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
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @GetMapping(path = "/getTemplates/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult>> getTemplates(@PathVariable Integer pageNo, Integer pageSize);

    /**
     * 查找模板对应的所有数据
     *
     * @param templateId
     * @return 模板数据
     */
    @ApiOperation(value = "查找模板对应的所有数据", nickname = "getAllTemplateData")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @GetMapping(path = "/getAllTemplateData/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<List<Domain>>> getAllTemplateData(Long templateId,@PathVariable Integer pageNo, Integer pageSize);

    /**
     * 单据历史
     *
     * @param organization 企业id
     * @return
     */
    @ApiOperation(value = "单据历史", nickname = "getAllDocument")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @GetMapping(path = "/getAllDocument")
    @ResponseBody
    ResponseEntity<Response<List>> getAllDocument(String organization);

}
