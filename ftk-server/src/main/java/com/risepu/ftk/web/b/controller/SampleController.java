/**
 *
 */
package com.risepu.ftk.web.b.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.RegistResult;

/**
 * @author q-wang
 */
@Controller
@RequestMapping(path = "/api/sample")
public class SampleController implements SampleApi {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ResponseEntity<Response<RegistResult>> regist() {
		logger.debug("Request Uri: /sample/sample1");
		Response<List<String>> sample1 = Response.succeed(Arrays.asList(new String[] { "hello", "world" }));
		RegistResult result = new RegistResult();
		return ResponseEntity.ok(Response.succeed(result));
	}
}
