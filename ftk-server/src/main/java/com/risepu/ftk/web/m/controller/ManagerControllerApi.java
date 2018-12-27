package com.risepu.ftk.web.m.controller;    /*
 * @author  Administrator
 * @date 2018/12/21
 */

import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationStream;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api("admin")
@RequestMapping("/api/admin")
public interface ManagerControllerApi {


    @ApiOperation(value = "管理端登录", nickname = "adminLogin")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/login")
    @ResponseBody
    ResponseEntity<Response<String>> orgLogin(@RequestParam(name = "name") String adminName,
                                              @RequestParam String password, HttpServletRequest request);


    @ApiOperation(value = "管理端修改密码", nickname = "changePwd")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/changePwd")
    @ResponseBody
    ResponseEntity<Response<String>> changePwd(@RequestParam String password,
                                               @RequestParam String newPwd, HttpServletRequest request);


    @ApiOperation(value = "查询发起认证的企业信息流水", nickname = "queryOrgList")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @RequestMapping(path = "/queryList/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult<OrganizationStream>>> queryOrganization(@RequestParam(required = false) String key,
                                                                               @PathVariable Integer pageNo,
                                                                               @RequestParam Integer pageSize,
                                                                               @RequestParam(required = false) String startTime,
                                                                               @RequestParam(required = false) String endTime,
                                                                               @RequestParam(required = false) Integer state,
                                                                               HttpServletRequest request);

    @ApiOperation(value = "查询单个企业流水的详情信息", nickname = "queryOne")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = Organization.class)})
    @RequestMapping(path = "/queryOne/{streamId:\\d+}")
    @ResponseBody
    ResponseEntity<Response<OrganizationStream>> queryOrgById(@PathVariable Long streamId);


    @ApiOperation(value = "保存修改后的企业信息", nickname = "editOrgInfo")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/editOrg")
    @ResponseBody
    ResponseEntity<Response<String>> checkOrgInfo(@RequestBody OrganizationStream organizationStream);


    @ApiOperation(value = "管理员退出登录", nickname = "loginOut")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/logout")
    @ResponseBody
    ResponseEntity<Response<String>> loginOut(HttpServletRequest request);


}




