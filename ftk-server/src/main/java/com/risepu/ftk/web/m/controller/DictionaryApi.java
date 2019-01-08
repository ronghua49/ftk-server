package com.risepu.ftk.web.m.controller;    /*
 * @author  Administrator
 * @date 2019/1/3
 */

import com.risepu.ftk.server.domain.Dictionary;
import com.risepu.ftk.server.domain.DictionaryData;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.m.dto.CodeAndName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author ronghaohua
 */
@Api("industry")
@RequestMapping("/api/industry")
public interface DictionaryApi {


    @ApiOperation(value = "查询所有行业分类分页", nickname = "allclasspage")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = PageResult.class) })
    @RequestMapping(path = "/all/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult<Dictionary>>> findAllPage(@RequestParam(required = false) String dictCode,
                                                                 @RequestParam(required = false) String name,
                                                                 @PathVariable Integer pageNo,
                                                                 @RequestParam Integer pageSize);


    @ApiOperation(value = "查询所有行业分类", nickname = "allclass")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = List.class) })
    @RequestMapping(path = "/all")
    @ResponseBody
    ResponseEntity<Response<List<CodeAndName>>> findAll();

    @ApiOperation(value = "增加行业分类", nickname = "addClass")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/addClass")
    @ResponseBody
    ResponseEntity<Response<String>>addClass(@RequestBody Dictionary dictionary);

    @ApiOperation(value = "查询单个分类", nickname = "")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = Dictionary.class) })
    @RequestMapping(path = "/dictCode")
    @ResponseBody
    ResponseEntity<Response<Dictionary>>queryClass(@RequestParam String dictCode);



    @ApiOperation(value = "修改行业分类", nickname = "edit")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/editClass")
    @ResponseBody
    ResponseEntity<Response<String>> editClass(@RequestBody Dictionary dictionary);


    @ApiOperation(value = "单个行业分类详情", nickname = "details")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @RequestMapping(path = "/detail/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult>> detail(@PathVariable Integer pageNo, Integer pageSize, @RequestParam(required = false) String code, @RequestParam(required = false) String name, Long dictId) throws UnsupportedEncodingException;


    @ApiOperation(value = "删除行业分类", nickname = "del")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = String.class)})
    @RequestMapping(path = "/delClass")
    @ResponseBody
    ResponseEntity<Response<String>> delClass(@RequestParam String dictCode);


    @ApiOperation(value = "查询所有二级行业", nickname = "secondClass")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @RequestMapping(path = "/allSecondclass")
    @ResponseBody
    ResponseEntity<Response<List<CodeAndName>>>allSecondclass();


    @ApiOperation(value = "查询特定二级行业列表", nickname = "secondClass")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = List.class)})
    @RequestMapping(path = "/secondclass")
    @ResponseBody
    ResponseEntity<Response<List<CodeAndName>>>secondclass(@RequestParam String dictCode);



    @ApiOperation(value = "单个行业", nickname = "oneIndustry")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = DictionaryData.class) })
    @RequestMapping(path = "/one")
    @ResponseBody
    ResponseEntity<Response<DictionaryData>>queryOne(@RequestParam Integer id);

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
    @RequestMapping(path = "/delIndustry")
    @ResponseBody
    ResponseEntity<Response<String>>delIndustry(@RequestParam Integer id);


}
