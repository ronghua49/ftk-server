package com.risepu.ftk.web.m.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.risepu.ftk.server.domain.OrganizationAdvice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationStream;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.UnsupportedEncodingException;


/**
 * @author ronghaohua
 */
@Api("admin")
@RequestMapping("/api/admin")
public interface ManagerControllerApi {

    @ApiOperation(value = "管理端登录", nickname = "adminLogin")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/login")
    @ResponseBody
    ResponseEntity<Response<String>> orgLogin(@RequestParam(name = "name") String adminName, @RequestParam String password, HttpServletRequest request);

    @ApiOperation(value = "当前登录用户", nickname = "loginUser")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping("/loginUser")
    @ResponseBody
    ResponseEntity<Response<String>> loginUser(HttpSession session);

    @ApiOperation(value = "管理端修改密码", nickname = "changePwd")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/changePwd")
    @ResponseBody
    ResponseEntity<Response<String>> changePwd(@RequestParam String password, @RequestParam String newPwd, HttpServletRequest request);

    @ApiOperation(value = "查询发起认证的企业信息", nickname = "queryOrgList")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @RequestMapping(path = "/queryList/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult<OrganizationStream>>> queryRegOrganization(@RequestParam(required = false) String key,
                                                                                  @RequestParam(required = false) String  applicationPhone,
                                                                                  @PathVariable Integer pageNo,
                                                                                  @RequestParam Integer pageSize,
                                                                                  @RequestParam(required = false) String startTime,
                                                                                  @RequestParam(required = false) String endTime,
                                                                                  @RequestParam(required = false) Integer state) throws UnsupportedEncodingException;

    @ApiOperation(value = "查询单个企业流水的详情信息", nickname = "queryOne")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = Organization.class)})
    @RequestMapping(path = "/queryOne/{streamId:\\d+}")
    @ResponseBody
    ResponseEntity<Response<OrganizationStream>> queryOrgById(@PathVariable Long streamId);

    @ApiOperation(value = "保存审核后的企业信息", nickname = "editOrgInfo")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/editOrg")
    @ResponseBody
    ResponseEntity<Response<String>> checkOrgInfo(@RequestBody OrganizationStream organizationStream);

    @ApiOperation(value = "管理员退出登录", nickname = "loginOut")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/logout")
    @ResponseBody
    ResponseEntity<Response<String>> loginOut(HttpServletRequest request);


    @ApiOperation(value = "企业反馈意见列表查询", nickname = "orgAdvice")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @RequestMapping(path = "/allAdvice/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult<OrganizationAdvice>>> queryAllAdvice(@RequestParam(required = false) String orgName,
                                                                            @RequestParam(required = false) String tel,
                                                                            @PathVariable Integer pageNo,
                                                                            @RequestParam Integer pageSize,
                                                                            @RequestParam(required = false) String startTime,
                                                                            @RequestParam(required = false) String endTime
    ) throws UnsupportedEncodingException;


    @ApiOperation(value = "企业反馈意见详情", nickname = "orgAdviceDetails ")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = OrganizationAdvice.class)})
    @RequestMapping(path = "/advice/{id:\\d+}")
    @ResponseBody
    ResponseEntity<Response<OrganizationAdvice>> queryAdvice(@PathVariable Long id);






}
