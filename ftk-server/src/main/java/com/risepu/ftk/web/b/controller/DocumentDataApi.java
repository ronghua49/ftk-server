package com.risepu.ftk.web.b.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.risepu.ftk.web.b.dto.RegistResult;

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
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed") })
	@RequestMapping(path = "/add")
	@ResponseBody
	public void add(Long template, JsonObject json, HttpSession session) throws Exception;

	/**
	 * 发送邮件
	 * 
	 * @return 邮件发送状态
	 */
	@ApiOperation(value = "发送邮件", nickname = "sendEmail")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
	@RequestMapping(path = "/sendEmail")
	@ResponseBody
	public String sendEmail(String email);
}
