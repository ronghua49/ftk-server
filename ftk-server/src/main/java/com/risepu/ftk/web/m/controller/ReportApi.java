package com.risepu.ftk.web.m.controller;

import com.risepu.ftk.server.domain.OrganizationStream;
import com.risepu.ftk.server.domain.RegisterUserReport;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.m.dto.DocumentRequest;
import com.risepu.ftk.web.m.dto.ExportRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

/**
 * @author L-heng
 */
@Api(value = "report")
public interface ReportApi {

    @ApiOperation(value = "证明统计汇总表", nickname = "getCount")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @GetMapping(path = "/getCount/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult>> getCount(@PathVariable Integer pageNo, Integer pageSize, Integer startYear, Integer endYear);


    @ApiOperation(value = "企业单据统计明细表", nickname = "getDocument")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @GetMapping(path = "/getDocument/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult>> getDocument(@PathVariable Integer pageNo,
                                                     Integer pageSize,
                                                     @RequestParam(required = false) String organization,
                                                     @RequestParam(required = false) String channelName,
                                                     @RequestParam(required = false) String createTime,
                                                     @RequestParam(required = false) String number,
                                                     @RequestParam(required = false) String type) throws UnsupportedEncodingException, ParseException;


    @ApiOperation(value = "企业认证统计", nickname = "orgRegisterReport")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/orgReg/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult<OrganizationStream>>> queryRegOrganization(@RequestParam(required = false) String orgName,
                                                                                  @RequestParam(required = false) String legalPerson,
                                                                                  @RequestParam(required = false) String industry,
                                                                                  @PathVariable Integer pageNo,
                                                                                  @RequestParam Integer pageSize,
                                                                                  @RequestParam(required = false) String startTime,
                                                                                  @RequestParam(required = false) String endTime,
                                                                                  @RequestParam(required = false) Integer state) throws UnsupportedEncodingException;

    @ApiOperation(value = "用户注册统计", nickname = "UserRegisterReport")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/userReg/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult<RegisterUserReport>>> queryRegUser(@RequestParam(required = false) String userType,
                                                                          @PathVariable Integer pageNo,
                                                                          @RequestParam Integer pageSize,
                                                                          @RequestParam(required = false) String startTime,
                                                                          @RequestParam(required = false) String endTime);


    @ApiOperation(value = "企业单据表导出excel", nickname = "exportExcel")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed")})
    @RequestMapping(path = "/doc/export")
    @ResponseBody
   void exportDocument(HttpServletResponse response, @RequestParam(required = false) String organization, @RequestParam(required = false) String channelName, @RequestParam(required = false) String createTime, @RequestParam(required = false) String number, @RequestParam(required = false) String type, @RequestParam(required = false) String  hashs)throws UnsupportedEncodingException, ParseException;


}
