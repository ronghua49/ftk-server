package com.risepu.ftk.web.b.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.web.api.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author L-heng
 *
 */
@Api(value = "template")
public interface TemplateApi {
	/**
	 * 显示所有模板
	 * 
	 * @return 模板所在地址
	 */
	@ApiOperation(value = "显示所有模板", nickname = "getAllTemplate")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = List.class) })
	@RequestMapping(path = "/getAllTemplate")
	@ResponseBody
	public ResponseEntity<Response<List<String>>> getAllTemplate();

	/**
	 * 查找模板对应的所有数据
	 * 
	 * @param templateId
	 * @return 模板数据
	 */
	@ApiOperation(value = "查找模板对应的所有数据", nickname = "getTemplateData")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = List.class) })
	@RequestMapping(path = "/getTemplateData")
	@ResponseBody
	public ResponseEntity<Response<List<Domain>>> getTemplateData(Long templateId);

	/**
	 * 添加模板
	 * 
	 * @param template 模板JavaBean
	 * @return 添加是否成功
	 */
	@ApiOperation(value = "添加模板", nickname = "addTemplate")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
	@RequestMapping(path = "/addTemplate", consumes = { "application/json" })
	@ResponseBody
	public ResponseEntity<Response<String>> addTemplate(@RequestBody Template template);

	/**
	 * 添加模板数据
	 * 
	 * @param domain 模板数据JavaBean
	 * @return 添加是否成功
	 */
	@ApiOperation(value = "添加模板数据", nickname = "addTemplateData")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
	@RequestMapping(path = "/addTemplateData", consumes = { "application/json" })
	@ResponseBody
	public ResponseEntity<Response<String>> addTemplateData(Long templateId, @RequestBody Domain domain);

}
