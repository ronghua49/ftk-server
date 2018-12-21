package com.risepu.ftk.web.m.controller;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.DomainService;
import com.risepu.ftk.server.service.TemplateDomainService;
import com.risepu.ftk.server.service.TemplateService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import net.lc4ever.framework.format.DateFormatter;
import org.apache.commons.lang3.StringUtils;
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
    public ResponseEntity<Response<PageResult>> getAllTemplate(Integer pageNo, Integer pageSize, String startTime, String endTime, String name) throws Exception {
        Integer firstIndex = pageNo * pageSize;
        Date startDate = null;
        Date endDate = null;
        if (startTime == null) {
            startTime = null;
            endTime = null;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startDate = formatter.parse(startTime);
            endDate = formatter.parse(endTime);
            endDate = DateFormatter.startOfDay(DateFormatter.nextDay(endDate));
        }
        String hql = "from Template where 1 = 1";
        if (StringUtils.isNotEmpty(startTime)) {
            hql += " and createTimestamp>='" + startDate + "' and createTimestamp<='" + endDate + "'";
        }
        if (StringUtils.isNotEmpty(name)) {
            hql += " and name like '%" + name + "%'";
        }
        List<Template> templates = templateService.getAllTemplate(hql);

        List list = templateService.getAnyTemplate(firstIndex, pageSize, hql);
        PageResult<Template> pageResult = new PageResult<>();
        pageResult.setResultCode("SUCCESS");
        pageResult.setNumber(pageNo);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages(templates.size(), pageSize);
        pageResult.setTotalElements(templates.size());
        pageResult.setContent(list);
        return ResponseEntity.ok(Response.succeed(pageResult));
    }

    @Override
    public ResponseEntity<Response<PageResult>> getAnyDomain(Integer pageNo, Integer pageSize, String code, String label) {
        Integer firstIndex = pageNo * pageSize;
        String hql = "from Domain where 1 = 1";
        if (StringUtils.isNotEmpty(code)) {
            hql += " and code = '" + code + "'";
        }
        if (StringUtils.isNotEmpty(label)) {
            hql += " and label = '" + label + "'";
        }
        List<Domain> domains = domainService.getDomains(hql);
        List list = domainService.getAnyDomain(firstIndex, pageSize, hql);
        PageResult<Domain> pageResult = new PageResult<>();
        pageResult.setResultCode("SUCCESS");
        pageResult.setNumber(pageNo);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages(domains.size(), pageSize);
        pageResult.setTotalElements(domains.size());
        pageResult.setContent(list);
        return ResponseEntity.ok(Response.succeed(pageResult));
    }

    @Override
    public ResponseEntity<Response<List<Domain>>> getAllDomain() {
        List<Domain> domains = domainService.selectAll();
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
        if (StringUtils.isEmpty(template.get_template())) {
            return ResponseEntity.ok(Response.failed(400, "二次模板不能为空"));
        }
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
        if (StringUtils.isEmpty(domain.getCode())) {
            return ResponseEntity.ok(Response.failed(400, "模板要素code不能为空"));
        } else if (StringUtils.isEmpty(domain.getLabel())) {
            return ResponseEntity.ok(Response.failed(400, "模板要素名称不能为空"));
        } else if (StringUtils.isEmpty(domain.getKegex())) {
            return ResponseEntity.ok(Response.failed(400, "校验规则不能为空"));
        }
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
