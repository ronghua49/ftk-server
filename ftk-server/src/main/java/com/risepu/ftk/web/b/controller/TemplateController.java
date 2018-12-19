package com.risepu.ftk.web.b.controller;

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
    public ResponseEntity<Response<Template>> getTemplate(Long templateId) {
        Template template = templateService.getTemplate(templateId);
        return ResponseEntity.ok(Response.succeed(template));
    }

    @Override
    public ResponseEntity<Response<List<Template>>> getAllTemplate() {
        List<Template> templates = templateService.select();
        return ResponseEntity.ok(Response.succeed(templates));
    }

    @Override
    public ResponseEntity<Response<List<Domain>>> getTemplateData(Long templateId) {
        List<Domain> domains = domainService.selectByTemplate(templateId);
        return ResponseEntity.ok(Response.succeed(domains));
    }

    @Override
    public ResponseEntity<Response<String>> updateTemplate(Template template) {
        templateService.update(template);
        String _templat = template.get_template();
        List<Domain> domains = domainService.selectAll();
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = domains.get(i);
            String code = "${" + domain.getCode() + "}";
            if (_templat.contains(code)) {
                templateDomainService.addOrUpdate(template.getId(), domain.getId());
            }
        }
        return ResponseEntity.ok(Response.succeed("添加成功"));
    }

    @Override
    public ResponseEntity<Response<String>> addTemplate(@RequestBody Template template) {
        // TODO Auto-generated method stub
        Long templateId = templateService.add(template);
        if (templateId != null) {
            String _templat = template.get_template();
            List<Domain> domains = domainService.selectAll();
            for (int i = 0; i < domains.size(); i++) {
                Domain domain = domains.get(i);
                String code = "${" + domain.getCode() + "}";
                if (_templat.contains(code)) {
                    templateDomainService.addOrUpdate(templateId, domain.getId());
                }
            }
            return ResponseEntity.ok(Response.succeed("添加成功"));
        }
        return ResponseEntity.ok(Response.failed(400, "添加失败"));
    }

    @Override
    public ResponseEntity<Response<String>> addTemplateData(Domain domain) {
        // TODO Auto-generated method stub
        Long domainId = domainService.add(domain);
        if (domainId != null) {
            return ResponseEntity.ok(Response.succeed("添加成功"));
        }
        return ResponseEntity.ok(Response.failed(400, "添加失败"));
    }

    @Override
    public ResponseEntity<Response<String>> updateTemplateState(Long templateId) {
        Template template = templateService.getTemplate(templateId);
        template.setState(1);
        return ResponseEntity.ok(Response.succeed("更改成功"));
    }
}
