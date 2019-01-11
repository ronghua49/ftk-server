package com.risepu.ftk.web.m.controller;    /*
 * @author  ronghaohua
 * @date 2019/1/11
 */

import com.risepu.ftk.server.domain.Channel;
import com.risepu.ftk.server.domain.Dictionary;
import com.risepu.ftk.server.domain.OrganizationAdvice;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Api(value = "channel")
@RequestMapping("/api/channel")
public interface ChannelApi {

    @ApiOperation(value = "渠道列表查询", nickname = "channelQuery")
    @ApiResponses({@ApiResponse(code = 200, message = "succeed", response = PageResult.class)})
    @RequestMapping(path = "/{pageNo:\\d+}")
    @ResponseBody
    ResponseEntity<Response<PageResult<Channel>>> queryAllAdvice(@RequestParam(required = false) String channelName,
                                                                 @RequestParam(required = false) String contactPerson,
                                                                 @PathVariable Integer pageNo,
                                                                 @RequestParam Integer pageSize,
                                                                 @RequestParam(required = false) String createTimestamp

    ) throws UnsupportedEncodingException;



    @ApiOperation(value = "增加渠道", nickname = "addChannel")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/add")
    @ResponseBody
    ResponseEntity<Response<String>> addChannel(@RequestBody Channel channel);


    @ApiOperation(value = "修改渠道", nickname = "editChannel")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = String.class) })
    @RequestMapping(path = "/edit")
    @ResponseBody
    ResponseEntity<Response<String>> editChannel(@RequestBody Channel channel);


    @ApiOperation(value = "单个渠道", nickname = "Channel")
    @ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = Channel.class) })
    @RequestMapping(path = "/one/{id:\\d+}")
    @ResponseBody
    ResponseEntity<Response<Channel>> queryOne(@PathVariable Long id);


}
