package com.risepu.ftk.web.m.controller;    /*
 * @author  Administrator
 * @date 2019/1/3
 */

import com.risepu.ftk.server.domain.OrganizationStream;
import com.risepu.ftk.server.domain.RegisterUserReport;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ronghaohua
 */
@Api("report")
@RequestMapping("/api/report")
public interface ReportControllerApi {

    @ApiOperation(value = "企业认证统计", nickname = "orgRegisterReport")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/orgReg/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult<OrganizationStream>>> queryRegOrganization(@RequestParam(required = false) String orgName,
                                                                                   @RequestParam(required = false) String legalPerson,
                                                                                   @RequestParam(required = false) String industry,
                                                                                   @PathVariable Integer pageNo,
                                                                                   @RequestParam Integer pageSize,
                                                                                   @RequestParam(required = false) String startTime,
                                                                                   @RequestParam(required = false) String endTime,
                                                                                   @RequestParam(required = false) Integer state);


    @ApiOperation(value = "用户注册统计", nickname = "UserRegisterReport")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/userReg/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult<RegisterUserReport>>> queryRegUser(@RequestParam(required = false) String userType,
                                                                          @PathVariable Integer pageNo,
                                                                          @RequestParam Integer pageSize,
                                                                          @RequestParam(required = false) String startTime,
                                                                          @RequestParam(required = false) String endTime);


}
