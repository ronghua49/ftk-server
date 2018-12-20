package com.risepu.ftk.web.b.controller;

import java.util.List;

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
public interface TemplateApi {
    /**
     * 根据id查找模板
     *
     * @return 模板JavaBean
     */
    @ApiOperation(value = "回显模板", nickname = "getTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = Template.class)})
    @GetMapping(path = "/getTemplate")
    @ResponseBody
    ResponseEntity<Response<Template>> getTemplate(Long templateId);

    /**
     * 显示所有模板
     *
     * @return 模板JavaBean集合
     */
    @ApiOperation(value = "显示所有模板", nickname = "getAllTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @GetMapping(path = "/getAllTemplate")
    @ResponseBody
    ResponseEntity<Response<List<Template>>> getAllTemplate();

    /**
     * 显示已启用模板
     *
     * @return 模板JavaBean集合
     */
    @ApiOperation(value = "显示已启用模板", nickname = "getTemplates")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @GetMapping(path = "/getTemplates")
    @ResponseBody
    ResponseEntity<Response<List<Template>>> getTemplates();

    /**
     * 查找模板对应的所有数据
     *
     * @param templateId
     * @return 模板数据
     */
    @ApiOperation(value = "查找模板对应的所有数据", nickname = "getTemplateData")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @GetMapping(path = "/getTemplateData")
    @ResponseBody
    ResponseEntity<Response<List<Domain>>> getTemplateData(Long templateId);

    /**
     * 修改模板
     *
     * @param template
     * @return
     */
    @ApiOperation(value = "修改模板", nickname = "updateTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @PostMapping(path = "/updateTemplate", consumes = {"application/json"})
    @ResponseBody
    ResponseEntity<Response<String>> updateTemplate(@RequestBody Template template);

    /**
     * 添加模板
     *
     * @param template 模板JavaBean
     * @return 添加是否成功
     */
    @ApiOperation(value = "添加模板", nickname = "addTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @PostMapping(path = "/addTemplate", consumes = {"application/json"})
    @ResponseBody
    ResponseEntity<Response<String>> addTemplate(@RequestBody Template template);

    /**
     * 添加模板数据
     *
     * @param domain 模板数据JavaBean
     * @return 添加是否成功
     */
    @ApiOperation(value = "添加模板数据", nickname = "addTemplateData")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @PostMapping(path = "/addTemplateData", consumes = {"application/json"})
    @ResponseBody
    ResponseEntity<Response<String>> addTemplateData(@RequestBody Domain domain);

    /**
     * 更新模板状态
     *
     * @param templateId 模板id
     * @return 是否更新成功
     */
    @ApiOperation(value = "更新模板状态", nickname = "updateTemplateState")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @GetMapping(path = "/updateTemplateState")
    @ResponseBody
    ResponseEntity<Response<String>> updateTemplateState(Long templateId);

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
