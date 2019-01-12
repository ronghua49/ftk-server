package com.risepu.ftk.web.m.controller;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.SimpleTemplate;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.m.dto.DomainRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author L-heng
 */
@Api(value = "template")
public interface ManageTemplateApi {


    @ApiOperation(value = "回显模板", nickname = "getTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = Template.class)})
    @GetMapping(path = "/getTemplate")
    @ResponseBody
    ResponseEntity<Response<Template>> getTemplate(Long templateId);


    @ApiOperation(value = "回显简易模板", nickname = "getSimpleTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = Template.class)})
    @GetMapping(path = "/getSimpleTemplate")
    @ResponseBody
    ResponseEntity<Response<SimpleTemplate>> getSimpleTemplate(Long id);


    @ApiOperation(value = "显示所有模板", nickname = "getAllTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @GetMapping(path = "/getAllTemplate/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult>> getAllTemplate(@PathVariable Integer pageNo, Integer pageSize, String startDate, String endDate, String name) throws Exception;


    @ApiOperation(value = "显示所有简易模板", nickname = "getAllSimpleTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @GetMapping(path = "/getAllSimpleTemplate/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult>> getAllSimpleTemplate(@PathVariable Integer pageNo, Integer pageSize, String code, String name) throws Exception;


    @ApiOperation(value = "根据模板id查找所有模板数据(有分页）", nickname = "getAnyDomain")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @GetMapping(path = "/getAnyDomain/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult>> getAnyDomain(@PathVariable Integer pageNo, Integer pageSize, String code, String label, Long templateId) throws UnsupportedEncodingException;


    @ApiOperation(value = "根据模板id查找所有模板数据(无分页）", nickname = "getAllDomain")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @GetMapping(path = "/getAllDomain")
    @ResponseBody
    ResponseEntity<Response<List<Domain>>> getAllDomain(String templateId);


    @ApiOperation(value = "添加模板", nickname = "addTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @PostMapping(path = "/addTemplate", consumes = {"application/json"})
    @ResponseBody
    ResponseEntity<Response<String>> addTemplate(@RequestBody Template template);


    @ApiOperation(value = "修改模板", nickname = "updateTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @PostMapping(path = "/updateTemplate", consumes = {"application/json"})
    @ResponseBody
    ResponseEntity<Response<String>> updateTemplate(@RequestBody Template template);


    @ApiOperation(value = "修改简易模板", nickname = "updateSimpleTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @PostMapping(path = "/updateSimpleTemplate", consumes = {"application/json"})
    @ResponseBody
    ResponseEntity<Response<String>> updateSimpleTemplate(@RequestBody SimpleTemplate simpleTemplate);


    @ApiOperation(value = "添加简易模板", nickname = "addSimpleTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @PostMapping(path = "/addSimpleTemplate", consumes = {"application/json"})
    @ResponseBody
    ResponseEntity<Response<String>> addSimpleTemplate(@RequestBody SimpleTemplate simpleTemplate) throws Exception;


    @ApiOperation(value = "添加简易模板数据", nickname = "addTemplateData")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @PostMapping(path = "/addTemplateData", consumes = {"application/json"})
    @ResponseBody
    ResponseEntity<Response<String>> addSimpleTemplateData(@RequestBody DomainRequest domainRequest);


    @ApiOperation(value = "回显模板数据", nickname = "getTemplateData")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = Domain.class)})
    @GetMapping(path = "/getTemplateData")
    @ResponseBody
    ResponseEntity<Response<Domain>> getTemplateData(Long domainId);


    @ApiOperation(value = "修改模板数据", nickname = "updateTemplateData")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @PostMapping(path = "/updateTemplateData", consumes = {"application/json"})
    @ResponseBody
    ResponseEntity<Response<String>> updateDomain(@RequestBody Domain domain);


    @ApiOperation(value = "模板名称下拉框", nickname = "TemplateList")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @GetMapping(path = "/TemplateList")
    @ResponseBody
    ResponseEntity<Response<List>> TemplateList();


    @ApiOperation(value = "删除模板数据", nickname = "deleteSimpleTemplateData")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @GetMapping(path = "/deleteSimpleTemplateData")
    @ResponseBody
    ResponseEntity<Response<String>> deleteSimpleTemplateData(Long domainId, Long templateId);


    @ApiOperation(value = "更新模板状态", nickname = "updateTemplateState")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @GetMapping(path = "/updateTemplateState")
    @ResponseBody
    ResponseEntity<Response<String>> updateTemplateState(Long templateId);
}
