package com.risepu.ftk.web.p.controller;    /*
 * @author  Administrator
 * @date 2018/12/21
 */

import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.PageRequest;
import com.risepu.ftk.web.b.dto.RegistRequest;
import com.risepu.ftk.web.p.dto.AuthHistoryInfo;
import com.risepu.ftk.web.p.dto.LoginRequest;
import com.risepu.ftk.web.p.dto.LoginResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api("personal")
@RequestMapping("/api/personal")
public interface PersonzalUserApi {

    @ApiOperation(value = "个人扫码", nickname = "scanDoc")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = LoginResult.class) })
    @RequestMapping(path = "/{hash:\\w+}")
    @ResponseBody
    ResponseEntity<Response<String>> personalScanDoc(@PathVariable String hash, HttpServletRequest request);

    @ApiOperation(value = "个人扫码登录", nickname = "login")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = LoginResult.class) })
    @RequestMapping(path = "/login")
    @ResponseBody
    ResponseEntity<Response<LoginResult>> personalLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest request);




    @ApiOperation(value = "个人点击授权或拒绝", nickname = "authentic orgScanRequest")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/authen")
    @ResponseBody
    ResponseEntity<Response<String>> personAuth(@RequestParam String streamId,@RequestParam String state, HttpServletRequest request);





    @ApiOperation(value = "个人授权历史查询", nickname = "authorization history")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = AuthHistoryInfo.class) })
    @RequestMapping(path = "/authen/history")
    @ResponseBody
    ResponseEntity<Response<PageResult<AuthHistoryInfo>>> getAuthInfoList(@RequestBody PageRequest pageRequest,
                                                                                 HttpServletRequest request);





}
