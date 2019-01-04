package com.risepu.ftk.web.m.controller;

import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author L-heng
 */
@Api(value = "report")
public interface ReportApi {
    /**
     * 证明统计汇总表
     *
     * @return 模板JavaBean
     */
    @ApiOperation(value = "证明统计汇总表", nickname = "getCount")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @GetMapping(path = "/getCount")
    @ResponseBody
    ResponseEntity<Response<List>> getCount(Integer startYear, Integer endYear);

    /**
     * 企业单据统计明细表
     *
     * @return 模板JavaBean
     */
    @ApiOperation(value = "企业单据统计明细表", nickname = "getDocument")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @GetMapping(path = "/getDocument")
    @ResponseBody
    ResponseEntity<Response<PageResult>> getDocument(Integer pageNo, Integer pageSize, String organization, String createTime, String number, String type);
}
