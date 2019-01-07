package com.risepu.ftk.web.b.controller;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.server.service.TemplateService;
import com.risepu.ftk.web.Constant;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.exception.NotLoginException;
import com.risepu.ftk.web.m.dto.IdRequest;
import net.lc4ever.framework.service.GenericCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author L-heng
 */
@Controller
@RequestMapping("/api/template")
public class CompanyTemplateController implements CompanyTemplateApi {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private GenericCrudService crudService;

    @Override
    public ResponseEntity<Response<List<Template>>> getTemplates(String defaultState, HttpServletRequest request) {
        OrganizationUser organizationUser = (OrganizationUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
        OrganizationUser user = organizationService.findOrgUserById(organizationUser.getId());
        if (user.getOrganizationId() == null) {
            return ResponseEntity.ok(Response.failed(400, "企业未认证"));
        }
        Organization org = organizationService.findAuthenOrgById(user.getOrganizationId());
        List<Template> templates = new ArrayList<>();
        if (defaultState.equals("0")) {
            Long flag = org.getDefaultTemId();
            if (org.getDefaultTemId() == null) {
                return ResponseEntity.ok(Response.failed(400, "无默认模板"));
            }
            Template template = templateService.getTemplate(org.getDefaultTemId());
            if (template.getState() == 1) {
                return ResponseEntity.ok(Response.failed(400, "该模板已下架"));
            }
            templates.add(template);
        }
        if (defaultState.equals("1")) {
            templates = templateService.getTemplates();
        }
        return ResponseEntity.ok(Response.succeed(templates));
    }

    @Override
    public ResponseEntity<Response<String>> getTemplateState(HttpServletRequest request) {
        OrganizationUser organizationUser = (OrganizationUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
        if (organizationUser == null) {
            throw new NotLoginException();
        }
        OrganizationUser user = organizationService.findOrgUserById(organizationUser.getId());
        if (user.getOrganizationId() == null) {
            return ResponseEntity.ok(Response.failed(400, "企业未认证"));
        }
        Organization org = organizationService.findAuthenOrgById(user.getOrganizationId());
        Integer state = crudService.uniqueResultHql(Integer.class, "select state from Template where id = ?1", org.getDefaultTemId());
        if (org.getDefaultTemId() != null && state == 0) {
            return ResponseEntity.ok(Response.succeed("0"));
        }
        return ResponseEntity.ok(Response.succeed("1"));
    }

    @Override
    public ResponseEntity<Response<List<Domain>>> getAllTemplateData(IdRequest templateId) {
        if (templateId.getTemplateId() == null) {
            return ResponseEntity.ok(Response.failed(400, "模板id不能为空"));
        }
        List<Domain> list = crudService.hql(Domain.class,
                "from Domain d where d.id in (select t.id.domainId from TemplateDomain t where t.id.templateId = ?1 )",
                templateId.getTemplateId());
        return ResponseEntity.ok(Response.succeed(list));
    }
}
