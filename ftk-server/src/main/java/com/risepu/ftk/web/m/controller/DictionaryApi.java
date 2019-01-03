package com.risepu.ftk.web.m.controller;    /*
 * @author  Administrator
 * @date 2019/1/3
 */

import com.risepu.ftk.server.domain.Dictionary;
import com.risepu.ftk.server.domain.DictionaryData;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ronghaohua
 */
@Api("industry")
@RequestMapping("/api/industry")
public interface DictionaryApi {


    @ApiOperation(value = "查询所有行业分类", nickname = "allclass")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/all/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult<Dictionary>>> findAll(@RequestParam(required = false) String key, @PathVariable Integer pageNo, @RequestParam Integer pageSize);



    @ApiOperation(value = "增加行业分类", nickname = "addClass")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/addClass")
    @ResponseBody
    ResponseEntity<Response<String>>addClass(@RequestBody Dictionary dictionary);


    @ApiOperation(value = "修改行业分类", nickname = "edit")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/edit")
    @ResponseBody
    ResponseEntity<Response<String>>editClass(@RequestBody Dictionary dictionary);


    @ApiOperation(value = "单个行业详情", nickname = "details")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/detail/{dictId:\\d+}")
    @ResponseBody
    ResponseEntity<Response<List<DictionaryData>>>detail(@RequestParam(required = false) String key, @PathVariable Integer dictId);


    @ApiOperation(value = "增加行业", nickname = "addIndustry")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/addIndustry")
    @ResponseBody
    ResponseEntity<Response<String>>addIndustry(@RequestBody DictionaryData dictionaryData);


    @ApiOperation(value = "修改行业", nickname = "editIndustry")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/editIndustry")
    @ResponseBody
    ResponseEntity<Response<String>>editIndustry(@RequestBody DictionaryData dictionaryData);

    @ApiOperation(value = "删除行业", nickname = "delIndustry")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/delIndustry/{id:\\d+}")
    @ResponseBody
    ResponseEntity<Response<String>>delIndustry(@PathVariable Integer id);


}
