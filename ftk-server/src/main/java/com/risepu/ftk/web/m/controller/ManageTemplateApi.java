package com.risepu.ftk.web.m.controller;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author L-heng
 */
@Api(value = "template")
public interface ManageTemplateApi {

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
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @GetMapping(path = "/getAllTemplate/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult>> getAllTemplate(@PathVariable Integer pageNo, Integer pageSize, String startDate, String endDate, String name) throws Exception;

    /**
     * 查找所有模板数据(有分页）
     *
     * @return
     */
    @ApiOperation(value = "查找所有模板数据(有分页）", nickname = "getAnyDomain")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @GetMapping(path = "/getAnyDomain/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult>> getAnyDomain(@PathVariable Integer pageNo, Integer pageSize, String code, String label);

    /**
     * 查找所有模板数据(无分页）
     *
     * @return
     */
    @ApiOperation(value = "查找所有模板数据(无分页）", nickname = "getAllDomain")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @GetMapping(path = "/getAllDomain")
    @ResponseBody
    ResponseEntity<Response<List<Domain>>> getAllDomain();

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
    ResponseEntity<Response<String>> updateTemplate(@RequestBody Template template) throws Exception;

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
    ResponseEntity<Response<String>> addTemplate(@RequestBody Template template) throws Exception;

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
     * 回显模板数据
     *
     * @param domainId 模板数据id
     * @return
     */
    @ApiOperation(value = "回显模板数据", nickname = "getTemplateData")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = Domain.class)})
    @GetMapping(path = "/getTemplateData")
    @ResponseBody
    ResponseEntity<Response<Domain>> getTemplateData(Long domainId);

    /**
     * 修改模板数据
     *
     * @param domain 模板数据JavaBean
     * @return
     */
    @ApiOperation(value = "修改模板数据", nickname = "updateTemplateData")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @PostMapping(path = "/updateTemplateData")
    @ResponseBody
    ResponseEntity<Response<String>> updateTemplateData(Domain domain);

    /**
     * 删除模板数据
     *
     * @param domainId
     * @return
     */
    @ApiOperation(value = "删除模板数据", nickname = "deleteTemplateData")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @GetMapping(path = "/deleteTemplateData")
    @ResponseBody
    ResponseEntity<Response<String>> deleteTemplateData(Long domainId);

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
}
