package com.risepu.ftk.web.b.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.RegistResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author q-wang
 */
@Api(value = "sample")
public interface SampleApi {

	@ApiOperation(value = "生成文档", nickname = "add")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
	@RequestMapping(path = "/add")
	@ResponseBody
	@CrossOrigin
	ResponseEntity<Response<String>> add(Long templateId, String _template, HttpServletResponse response) throws Exception;

	@ApiOperation(value = "企业注册", nickname = "regist")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = RegistResult.class) })
	@RequestMapping(path = "/orgRegist")
	@ResponseBody
	ResponseEntity<Response<RegistResult>> regist();

}
