package com.risepu.ftk.web.b.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.risepu.ftk.server.domain.Organization;
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
	@RequestMapping(path = "/regist")
	@ResponseBody
	@CrossOrigin
	ResponseEntity<Response<RegistResult>> orgRegist(@RequestBody RegistRequest registVo, HttpServletRequest request);

	@ApiOperation(value = "企业登录", nickname = "login")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = LoginResult.class) })
	@RequestMapping(path = "/login")
	@ResponseBody
	@CrossOrigin
	ResponseEntity<Response<LoginResult>> orgLogin(@RequestParam(name = "name") String mobileOrName, @RequestParam String password, HttpServletRequest request);

	@ApiOperation(value = "忘记密码", nickname = "forgetPwd")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
	@RequestMapping(path = "/forgetPwd")
	@ResponseBody
	@CrossOrigin
	ResponseEntity<Response<String>> orgForgetPwd(@RequestBody ForgetRequest forgetRequest, HttpServletRequest request);

	@ApiOperation(value = "修改密码", nickname = "changePwd")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
	@RequestMapping(path = "/changePwd")
	@ResponseBody
	@CrossOrigin
	ResponseEntity<Response<String>> orgChangePwd(@RequestParam String password, @RequestParam String newpwd, HttpServletRequest request);

	@ApiOperation(value = "图片上传", nickname = "imgupload")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class), @ApiResponse(code = 500, message = "failed", response = String.class) })
	@RequestMapping(path = "/img/upload")
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<Response<String>> upload(MultipartFile file);

	@ApiOperation(value = "图片下载", nickname = "imgdownload")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class), @ApiResponse(code = 500, message = "failed", response = String.class) })
	@RequestMapping(path = "/img/download/{imgName:\\w+}")
	@ResponseBody
	@CrossOrigin
	ResponseEntity<Response<String>> imgDownload(@PathVariable String imgName, HttpServletResponse response);

	@ApiOperation(value = "图片下载", nickname = "imgdownload")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
	@RequestMapping(path = "/authen/info")
	@ResponseBody
	@CrossOrigin
	ResponseEntity<Response<String>> orgAuthen(@RequestBody Organization organization, HttpServletRequest request);

}
