package com.risepu.ftk.web.m.controller;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.DomainService;
import com.risepu.ftk.server.service.TemplateDomainService;
import com.risepu.ftk.server.service.TemplateService;
import com.risepu.ftk.web.api.Response;
import net.lc4ever.framework.format.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author L-heng
 */
@Controller
@RequestMapping("/api/template")
public class ManageTemplateController implements ManageTemplateApi {
    @Autowired
    private TemplateService templateService;

    @Autowired
    private DomainService domainService;

    @Autowired
    private TemplateDomainService templateDomainService;

    @Override
    public ResponseEntity<Response<List<Template>>> getAllTemplate(Integer page, Integer pageSize, String startTime, String endTime, String name) throws Exception {
        Date startDate = null;
        Date endDate = null;
        if (startTime.trim() == null) {
            startTime = null;
            endTime = null;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startDate = formatter.parse(startTime);
            endDate = formatter.parse(endTime);
            endDate = DateFormatter.startOfDay(DateFormatter.nextDay(endDate));
        }
        String hql = "from Template where 1=1";
        if (startDate != null) {
            hql += "and createTimestamp>=" + startDate + "and createTimestamp<=" + endDate;
        }
        if (name.trim() != null) {
            hql += "and name like '%" + name + "%'";
        }
        List<Template> templates = templateService.getAllTemplate(hql);
        return ResponseEntity.ok(Response.succeed(templates));
    }

    @Override
    public ResponseEntity<Response<List<Domain>>> getAllDomain() {
        List<Domain> list = domainService.selectAll();
        return ResponseEntity.ok(Response.succeed(list));
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
    public ResponseEntity<Response<Domain>> getTemplateData(Long domainId) {
        return ResponseEntity.ok(Response.succeed(domainService.selectById(domainId)));
    }

    @Override
    public ResponseEntity<Response<String>> updateTemplateData(Domain domain) {
        domainService.update(domain);
        return ResponseEntity.ok(Response.succeed("修改成功"));
    }

    @Override
    public ResponseEntity<Response<String>> deleteTemplateData(Long domainId) {
        domainService.deleteById(domainId);
        return ResponseEntity.ok(Response.succeed("删除成功"));
    }

    @Override
    public ResponseEntity<Response<String>> updateTemplateState(Long templateId) {
        Template template = templateService.getTemplate(templateId);
        template.setState(1);
        return ResponseEntity.ok(Response.succeed("更改成功"));
    }
}
