/**
 *
 */
package com.risepu.ftk.web.b.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.risepu.ftk.server.service.SampleService;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.RegistResult;

/**
 * @author q-wang
 */
@Controller
@RequestMapping(path = "/sample")
public class SampleController implements org.springframework.web.servlet.mvc.Controller {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	@Autowired
	private SampleService sampleService;

	@RequestMapping(path = "/sample1")
	@ResponseBody
	public Response<RegistResult> regist() throws Exception {

		logger.debug("Request Uri: /sample/sample1");
		Response<List<String>> sample1 = Response.succeed(Arrays.asList(new String[] { "hello", "world" }));

		RegistResult result = new RegistResult();
		Response.failed(400, "错误请求");
		return Response.succeed(result);
	}
}
