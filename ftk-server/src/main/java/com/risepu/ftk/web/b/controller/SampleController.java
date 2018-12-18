package com.risepu.ftk.web.b.controller;

import java.util.Arrays;
import java.util.List;

import com.risepu.ftk.server.service.PdfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.RegistResult;

import javax.servlet.http.HttpServletResponse;

/**
 * @author q-wang
 */
@Controller
@RequestMapping(path = "/api/sample")
public class SampleController implements SampleApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PdfService pdfService;

    @Override
    public ResponseEntity<Response<RegistResult>> regist() {
        logger.debug("Request Uri: /sample/sample1");
        Response<List<String>> sample1 = Response.succeed(Arrays.asList(new String[]{"hello", "world"}));
        RegistResult result = new RegistResult();
        return ResponseEntity.ok(Response.succeed(result));
    }

    @Override
    public ResponseEntity<Response<String>> add(Long templateId, String _template, HttpServletResponse response) throws Exception {
        pdfService.pdf(templateId, _template,response);
        return ResponseEntity.ok(Response.succeed("生成文档成功"));
    }
}
