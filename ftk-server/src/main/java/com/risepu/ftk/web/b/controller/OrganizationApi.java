package com.risepu.ftk.web.b.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.risepu.ftk.server.domain.OrganizationAdvice;
import com.risepu.ftk.server.domain.OrganizationStream;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.b.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.risepu.ftk.web.api.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "org")
@RequestMapping("/api/org")
public interface OrganizationApi {

    @ApiOperation(value = "企业注册", nickname = "regist")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/regist")
    @ResponseBody
    ResponseEntity<Response<String>> orgRegist(@RequestBody RegistRequest registVo, HttpSession session);


    @ApiOperation(value = "企业登录", nickname = "Orglogin")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = LoginResult.class)})
    @RequestMapping(path = "/login")
    @ResponseBody
    ResponseEntity<Response<LoginResult>> orgLogin(@RequestBody OrgLoginRequest loginRequest, HttpSession session);

    @ApiOperation(value = "忘记密码", nickname = "forgetPwd")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/forgetPwd")
    @ResponseBody
    ResponseEntity<Response<String>> orgForgetPwd(@RequestBody ForgetRequest forgetRequest, HttpSession session);

    @ApiOperation(value = "修改密码", nickname = "changePwd")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/changePwd")
    @ResponseBody
    ResponseEntity<Response<String>> orgChangePwd(@RequestParam String password, @RequestParam String newpwd, HttpSession session);


    @ApiOperation(value = "图片上传", nickname = "imgupload")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class), @ApiResponse(code = 500, message = "failed", response = String.class)})
    @RequestMapping(path = "/img/upload")
    @ResponseBody
    ResponseEntity<Response<String>> upload(MultipartFile file);


    @ApiOperation(value = "图片下载", nickname = "imgdownload")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class),
            @ApiResponse(code = 500, message = "failed", response = String.class)})
    @RequestMapping(path = "/img/download/{imgName:\\w+.[a-z]{0,4}}")
    @ResponseBody
    ResponseEntity<Response<String>> imgDownload(@PathVariable String imgName, HttpServletResponse response);

    @ApiOperation(value = "企业发起的认证信息", nickname = "orgInfo")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/authen/info")
    @ResponseBody
    ResponseEntity<Response<String>> orgAuthen(@RequestBody OrganizationStream organizationStream,
                                               HttpSession session);

    @ApiOperation(value = "校验企业认证状态", nickname = "checkState")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = OrganizationStream.class)})
    @RequestMapping(path = "/auth/check")
    @ResponseBody
    ResponseEntity<Response<OrganizationStream>> checkAuthState(HttpSession session);


    @ApiOperation(value = "企业扫码", nickname = "scanQR")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = Long.class)})
    @RequestMapping(path = "/scanQR")
    @ResponseBody
    ResponseEntity<Response<Long>> scanQR(@RequestParam String hash, HttpSession session);


    @ApiOperation(value = "企业扫码历史单据查询", nickname = "QRHistory")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @RequestMapping(path = "/history/verify")
    @ResponseBody
    ResponseEntity<Response<PageResult>> verifyHistory(@RequestBody PageRequest pageRequest, HttpSession session);


    @ApiOperation(value = "企业开单历史", nickname = "documentHistory")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @RequestMapping(path = "/history/document")
    @ResponseBody
    ResponseEntity<Response<PageResult>> documentHistory(@RequestBody PageRequest pageRequest, HttpSession session);


    @ApiOperation(value = "开单历史详情", nickname = "documentInfo")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/history/documentInfo")
    @ResponseBody
    ResponseEntity<Response<String>> documentInfo(@RequestParam String chainHash);


    @ApiOperation(value = "企业反馈意见", nickname = "advice")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/advice")
    @ResponseBody
    ResponseEntity<Response<String>> adviceInfo(@RequestBody OrganizationAdvice advice,
                                                HttpSession session);


    @ApiOperation(value = "退出登录", nickname = "loginout")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/logout")
    @ResponseBody
    ResponseEntity<Response<String>> loginOut(HttpSession session);


    @ApiOperation(value = "设置默认模板", nickname = "defaultTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/defaultTem")
    @ResponseBody
    ResponseEntity<Response<String>> setDefaultTemplate(@RequestParam String templateId, @RequestParam boolean state, HttpSession session);


    @ApiOperation(value = "企业单据验证", nickname = "verifyDocument")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/verify")
    @ResponseBody
    ResponseEntity<Response<String>> qualifyQRCode(@RequestBody VerifyRequest verifyRequest);


}
