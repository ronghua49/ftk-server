package com.risepu.ftk.web.b.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.DomainService;
import com.risepu.ftk.server.service.TemplateService;
import com.risepu.ftk.web.api.Response;

/**
 * @author L-heng
 */
@Controller
@RequestMapping("/api/template")
public class CompanyTemplateController implements CompanyTemplateApi {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private DomainService domainService;

    @Override
    public ResponseEntity<Response<List<String>>> getTemplates() {
        List<Template> templates = templateService.getTemplates();
        List<String> list = null;
        for (int i = 0; i < templates.size(); i++) {
            list.add(templates.get(i).getFilePath());
        }
        return ResponseEntity.ok(Response.succeed(list));
    }

    @Override
    public ResponseEntity<Response<List<Domain>>> getAllTemplateData(Long templateId) {
        List<Domain> domains = domainService.selectByTemplate(templateId);
        return ResponseEntity.ok(Response.succeed(domains));
    }
}
