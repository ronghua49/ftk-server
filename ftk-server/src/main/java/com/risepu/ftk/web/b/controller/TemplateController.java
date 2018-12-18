package com.risepu.ftk.web.b.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.DomainService;
import com.risepu.ftk.server.service.TemplateDomainService;
import com.risepu.ftk.server.service.TemplateService;
import com.risepu.ftk.web.api.Response;

/**
 * @author L-heng
 */
@Controller
@RequestMapping("/api/template")
public class TemplateController implements TemplateApi {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private DomainService domainService;

    @Autowired
    private TemplateDomainService templateDomainService;

    @Override
    public ResponseEntity<Response<List<String>>> getAllTemplate() {
        List<Template> templates = templateService.select();
        List<String> filePath = new ArrayList<>();
        for (Template template : templates) {
            filePath.add(template.getFilePath());
        }
        return ResponseEntity.ok(Response.succeed(filePath));
    }

    @Override
    public ResponseEntity<Response<List<Domain>>> getTemplateData(Long templateId) {
        List<Domain> domains = domainService.selectByTemplate(templateId);
        return ResponseEntity.ok(Response.succeed(domains));
    }

    @Override
    public ResponseEntity<Response<String>> addTemplate(@RequestBody Template template) {
        // TODO Auto-generated method stub
        if (templateService.add(template) != null) {
            return ResponseEntity.ok(Response.succeed("添加成功"));
        }
        return ResponseEntity.ok(Response.failed(400, "添加失败"));
    }

    @Override
    public ResponseEntity<Response<String>> addTemplateData(Long templateId, Domain domain) {
        // TODO Auto-generated method stub
        Long domainId = domainService.add(domain);
        if (domainId != null) {
            templateDomainService.add(templateId, domainId);
            return ResponseEntity.ok(Response.succeed("添加成功"));
        }
        return ResponseEntity.ok(Response.failed(400, "添加失败"));
    }

}
