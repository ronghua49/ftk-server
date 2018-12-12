/**
 *
 */
package com.risepu.ftk.web.b.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.RegistResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author q-wang
 */
@Api(value = "sample")
public interface SampleApi {

	@ApiOperation(value = "示例", nickname = "regist")
	@ApiResponses({ @ApiResponse(code = 200, message = "succeed", response = RegistResult.class) })
	@RequestMapping(path = "/sample1")
	@ResponseBody
	ResponseEntity<Response<RegistResult>> regist();
}
