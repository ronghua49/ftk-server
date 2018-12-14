package com.risepu.ftk.web.b.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.ForgetRequest;
import com.risepu.ftk.web.b.dto.LoginResult;
import com.risepu.ftk.web.b.dto.RegistRequest;
import com.risepu.ftk.web.b.dto.RegistResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "org")
public interface OrganizationApi {

	@ApiOperation(value = "企业注册", nickname = "regist")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = RegistResult.class) })
	@RequestMapping(path = "/orgRegist")
	@ResponseBody
	ResponseEntity<Response<RegistResult>> orgRegist(@RequestBody RegistRequest registVo, HttpServletRequest request);

	@ApiOperation(value = "企业登录", nickname = "regist")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = LoginResult.class) })
	@RequestMapping(path = "/login")
	@ResponseBody
	ResponseEntity<Response<LoginResult>> orgLogin(@RequestParam(name = "name") String mobileOrName,
			@RequestParam String password, HttpServletRequest request);

	@ApiOperation(value = "修改密码", nickname = "regist")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
	@RequestMapping(path = "/modify/pwd")
	@ResponseBody
	ResponseEntity<Response<String>> orgChangPwd(@RequestBody ForgetRequest forgetRequest, HttpServletRequest request);

}
