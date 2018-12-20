package com.risepu.ftk.web.b.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.risepu.ftk.server.domain.DocumentData;
import com.risepu.ftk.server.domain.ProofDocument;
import com.risepu.ftk.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Template;
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

    @Autowired
    private ProofDocumentService proofDocumentService;

    @Autowired
    private DocumentDateService documentDateService;

    @Override
    public ResponseEntity<Response<Template>> getTemplate(Long templateId) {
        Template template = templateService.getTemplate(templateId);
        return ResponseEntity.ok(Response.succeed(template));
    }

    @Override
    public ResponseEntity<Response<List<Template>>> getAllTemplate() {
        List<Template> templates = templateService.getAllTemplate();
        return ResponseEntity.ok(Response.succeed(templates));
    }

    @Override
    public ResponseEntity<Response<List<Template>>> getTemplates() {
        List<Template> templates = templateService.getTemplates();
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
        List<Domain> list = domainService.selectByTemplate(template.getId());
        for (int j = 0; j < list.size(); j++) {
            Domain domain = list.get(j);
            templateDomainService.delete(template.getId(), domain.getId());
        }
        String _templat = template.get_template();
        List<Domain> domains = domainService.selectAll();
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = domains.get(i);
            String code = "${" + domain.getCode() + "}";
            if (_templat.contains(code)) {
                templateDomainService.add(template.getId(), domain.getId());
            }
        }
        return ResponseEntity.ok(Response.succeed("更新成功"));
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
                    templateDomainService.add(templateId, domain.getId());
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

    @Override
    public ResponseEntity<Response<List>> getAllDocument(String organization) {
        List<ProofDocument> proofDocuments = proofDocumentService.getByOrganization(organization);
        List list = new ArrayList();
        for (int i = 0; i < proofDocuments.size(); i++) {
            Map map = new HashMap();
            ProofDocument proofDocument = proofDocuments.get(i);
            List<DocumentData> documentDataList = documentDateService.getByDocumentId(proofDocument.getId());
            map.put("区块链哈希", proofDocument.getChainHash());
            map.put("单号", proofDocument.getId());
            for (int j = 0; j < documentDataList.size(); j++) {
                DocumentData documentData = documentDataList.get(j);
                Domain domain = domainService.selectById(documentData.getId().getDomainId());
                map.put(domain.getLabel(), documentData.getValue());
            }
            list.add(map);
        }
        return ResponseEntity.ok(Response.succeed(list));
    }
}
