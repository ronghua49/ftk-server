package com.risepu.ftk.web.b.controller;

import java.util.ArrayList;
import java.util.List;

import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.web.Constant;
import com.risepu.ftk.web.m.dto.IdRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.DomainService;
import com.risepu.ftk.server.service.TemplateService;
import com.risepu.ftk.web.api.Response;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private OrganizationService organizationService;

    @Override
    public ResponseEntity<Response<List<Template>>> getTemplates(HttpServletRequest request) {
        OrganizationUser organizationUser = (OrganizationUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
        Organization org = organizationService.findAuthenOrgById(organizationUser.getOrganizationId());
        List<Template> templates = new ArrayList<>();
        if (org.getDefaultTemId() != null) {
            Template template = templateService.getTemplate(org.getDefaultTemId());
            templates.add(template);

        } else {
            templates = templateService.getTemplates();
        }

        return ResponseEntity.ok(Response.succeed(templates));
    }

    @Override
    public ResponseEntity<Response<List<Domain>>> getAllTemplateData(IdRequest templateId) {
        List<Domain> domains = domainService.selectByTemplate(templateId.getTemplateId());
        return ResponseEntity.ok(Response.succeed(domains));
    }
}
