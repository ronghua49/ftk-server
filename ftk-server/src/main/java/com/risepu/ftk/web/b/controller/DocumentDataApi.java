package com.risepu.ftk.web.b.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
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
@Api(value = "documentData")
public interface DocumentDataApi {
	/**
	 * 生成文档
	 * 
	 * @param template
	 * @param json
	 * @throws Exception
	 */
	@ApiOperation(value = "生成文档", nickname = "add")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
	@RequestMapping(path = "/add")
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<Response<String>> add(Long template) throws Exception;

	/**
	 * 发送邮件
	 * 
	 * @return 邮件发送状态
	 * @throws Exception
	 */
	@ApiOperation(value = "发送邮件", nickname = "sendEmail")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
	@RequestMapping(path = "/sendEmail")
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<Response<String>> sendEmail(String email) throws Exception;
}
