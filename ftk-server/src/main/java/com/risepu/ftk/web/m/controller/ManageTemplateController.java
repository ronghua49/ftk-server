package com.risepu.ftk.web.m.controller;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.SimpleTemplate;
import com.risepu.ftk.server.domain.SimpleTemplateDomain;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.*;
import com.risepu.ftk.utils.ChartGraphics;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.utils.StringUtil;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.m.dto.DomainRequest;
import net.lc4ever.framework.format.DateFormatter;
import net.lc4ever.framework.service.GenericCrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private QrCodeUtilSerevice qrCodeUtilSerevice;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private GenericCrudService crudService;

    @Value("${ftk.root.filePath}")
    private String filePath;

    private Integer t = 0;

    @Override
    public ResponseEntity<Response<Template>> getTemplate(Long templateId) {
        if (templateId == null) {
            return ResponseEntity.ok(Response.failed(400, "模板id不能为空"));
        }
        Template template = templateService.getTemplate(templateId);
        return ResponseEntity.ok(Response.succeed(template));
    }

    @Override
    public ResponseEntity<Response<SimpleTemplate>> getSimpleTemplate(Long id) {
        if (id == null) {
            return ResponseEntity.ok(Response.failed(400, "模板id不能为空"));
        }
        SimpleTemplate simpleTemplate = crudService.uniqueResultHql(SimpleTemplate.class, "from SimpleTemplate where id = ?1", id);
        return ResponseEntity.ok(Response.succeed(simpleTemplate));
    }

    @Override
    public ResponseEntity<Response<PageResult>> getAllTemplate(Integer pageNo, Integer pageSize, String startTime, String endTime, String name) throws Exception {
        Integer firstIndex = pageNo * pageSize;
        Date endDate = null;
        if (StringUtils.isEmpty(startTime)) {
            startTime = null;
            endTime = null;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            endDate = formatter.parse(endTime);
            endDate = DateFormatter.startOfDay(DateFormatter.nextDay(endDate));
            endTime = formatter.format(endDate);
        }
        String hql = "from Template where 1 = 1";
        if (endTime != null) {
            hql += " and createTimestamp >= '" + startTime + "' and createTimestamp < '" + endTime + "'";
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
    public ResponseEntity<Response<PageResult>> getAllSimpleTemplate(Integer pageNo, Integer pageSize, String code, String name) throws Exception {
        Integer firstIndex = pageNo * pageSize;
        String hql = "from SimpleTemplate where 1 = 1";
        if (StringUtils.isNotEmpty(code)) {
            hql += " and code like '%" + code + "%'";
        }
        if (StringUtils.isNotEmpty(name)) {
            hql += " and name like '%" + name + "%'";
        }
        List<SimpleTemplate> templates = crudService.hql(SimpleTemplate.class, hql);

        List list = crudService.hql(firstIndex, pageSize, hql);
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
    public ResponseEntity<Response<PageResult>> getAnyDomain(Integer pageNo, Integer pageSize, String code, String label, Long templateId) {
        Integer firstIndex = pageNo * pageSize;
        String hql = "from Domain d where d.id in (select t.id.domainId from SimpleTemplateDomain t where t.id.templateId = " + templateId + ")";
        if (StringUtils.isNotEmpty(code)) {
            hql += " and d.code = '" + code + "'";
        }
        if (StringUtils.isNotEmpty(label)) {
            hql += " and d.label = '" + label + "'";
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
    public ResponseEntity<Response<List<Domain>>> getAllDomain(String templateId) {
        SimpleTemplate simpleTemplate = crudService.uniqueResultHql(SimpleTemplate.class, "from SimpleTemplate where code = ?1", templateId);
        List<Domain> list = crudService.hql(Domain.class,
                "from Domain d where d.id in (select t.id.domainId from SimpleTemplateDomain t where t.id.templateId = ?1 )",
                simpleTemplate.getId());
        return ResponseEntity.ok(Response.succeed(list));
    }


    @Override
    public ResponseEntity<Response<String>> addTemplate(Template template) {
        try {
            SimpleTemplate simpleTemplate = crudService.uniqueResultHql(SimpleTemplate.class, "from SimpleTemplate where code = ?1", template.getCode());
            List<Domain> list = crudService.hql(Domain.class,
                    "from Domain d where d.id in (select t.id.domainId from SimpleTemplateDomain t where t.id.templateId = ?1 )",
                    simpleTemplate.getId());
            List<String> list1 = new ArrayList();
            for (Domain domain : list) {
                list1.add(domain.getCode());
            }
            Template template1 = new Template();
            template1.setCode(simpleTemplate.getCode());
            template1.setName(simpleTemplate.getName());
            Long templateId = templateService.add(template1);
            String _template = template.get_template();

            List<Domain> domains = domainService.selectAll();
            for (int i = 0; i < domains.size(); i++) {
                Domain domain = domains.get(i);
                String code = "${" + domain.getCode() + "}";
                if (_template.contains(code)) {
                    _template = _template.replace(code, "____");
                    templateDomainService.add(templateId, domain.getId());
                }
            }
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM/dd");
            String date = ft.format(new Date());
            File file = new File(filePath + date);
            if (!file.exists()) {
                file.mkdirs();
            }
            StringUtil stringUtil = new StringUtil();
            List<String> list2 = stringUtil.getStrContainData(template.get_template(), "{", "}", true);
            for (String key : list2) {
                if (!list1.contains(key)) {
                    crudService.delete(templateService.getTemplate(templateId));
                    return ResponseEntity.ok(Response.failed(400, key + "不存在，请仔细检查！"));
                }
            }
            ChartGraphics cg = new ChartGraphics();
            String GrFilePath = cg.graphicsGeneration("******有限公司", filePath + date + "/示例盖章.jpg");
            String pdfFilePath = filePath + date + "/" + template.getId() + "（" + t++ + ").pdf";
            String qrFilePath = qrCodeUtilSerevice.createQrCode(filePath + date + "/示例二维码.jpg", "china is good");
            String filePath1 = pdfService.pdf(_template, "97481fb743487be151082fde934762eb9e3366a3", simpleTemplate.getName(), qrFilePath, GrFilePath, pdfFilePath);
            Template template2 = templateService.getTemplate(templateId);
            template2.setHashSize(template.getHashSize());
            template2.set_template(template.get_template());
            template2.setTitleSize(template.getTitleSize());
            template2.setContentSize(template.getContentSize());
            template2.setFilePath(filePath1);
            template2.setDescription(template.getDescription());
            templateService.update(template2);
            return ResponseEntity.ok(Response.succeed("添加成功"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Response.failed(400, "添加失败"));
        }
    }

    @Override
    public ResponseEntity<Response<String>> updateTemplate(Template template) {
        try {
            if (template.getId() == null) {
                return ResponseEntity.ok(Response.succeed("模板id不能为空"));
            }
            //根据id获取模板
            Template template1 = templateService.getTemplate(template.getId());
            //根据模板获取模板数据
            List<Domain> list = domainService.selectByTemplate(template.getId());
            for (int j = 0; j < list.size(); j++) {
                Domain domain = list.get(j);
                templateDomainService.delete(template.getId(), domain.getId());
            }
            String _template = template.get_template();
            List<Domain> domains = domainService.selectAll();

            SimpleTemplate simpleTemplate = crudService.uniqueResultHql(SimpleTemplate.class, "from SimpleTemplate where code = ?1", template.getCode());
            for (int i = 0; i < domains.size(); i++) {
                Domain domain = domains.get(i);
                String code = "${" + domain.getCode() + "}";
                if (_template.contains(code)) {
                    _template = _template.replace(code, "____");
                }
            }
            List<Domain> domainList = crudService.hql(Domain.class,
                    "from Domain d where d.id in (select t.id.domainId from SimpleTemplateDomain t where t.id.templateId = ?1 )",
                    simpleTemplate.getId());
            List<String> list1 = new ArrayList();
            for (Domain domain : domainList) {
                list1.add(domain.getCode());
                templateDomainService.add(template.getId(), domain.getId());
            }
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM/dd");
            String date = ft.format(new Date());

            File file = new File(filePath + date);
            if (!file.exists()) {
                file.mkdirs();
            }
            StringUtil stringUtil = new StringUtil();
            List<String> list2 = stringUtil.getStrContainData(template.get_template(), "{", "}", true);
            for (String key : list2) {
                if (!list1.contains(key)) {
                    crudService.delete(templateService.getTemplate(template.getId()));
                    return ResponseEntity.ok(Response.failed(400, key + "不存在，请仔细检查！"));
                }
            }

            ChartGraphics cg = new ChartGraphics();
            String GrFilePath = cg.graphicsGeneration("******有限公司", filePath + date + "/示例盖章.jpg");
            String pdfFilePath = filePath + date + "/" + template.getId() + "（" + t++ + ").pdf";
            String qrFilePath = qrCodeUtilSerevice.createQrCode(filePath + date + "/示例二维码.jpg", "china is good");
            String filePath1 = pdfService.pdf(_template, "97481fb743487be151082fde934762eb9e3366a3", simpleTemplate.getName(), qrFilePath, GrFilePath, pdfFilePath);
            template1.set_template(template.get_template());
            template1.setDescription(template.getDescription());
            template1.setName(simpleTemplate.getName());
            template1.setCode(simpleTemplate.getCode());
            template1.setFilePath(filePath1);
            template1.setContentSize(template.getContentSize());
            template1.setTitleSize(template.getTitleSize());
            template1.setHashSize(template.getHashSize());
            templateService.update(template1);
            return ResponseEntity.ok(Response.succeed("更新成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Response.failed(400, "更新失败"));
        }
    }

    @Override
    public ResponseEntity<Response<String>> updateSimpleTemplate(SimpleTemplate simpleTemplate) {
        if (simpleTemplate.getId() == null) {
            return ResponseEntity.ok(Response.failed(400, "模板id不能为空"));
        }
        if (StringUtils.isEmpty(simpleTemplate.getName())) {
            return ResponseEntity.ok(Response.failed(400, "模板名称不能为空"));
        }
        if (StringUtils.isEmpty(simpleTemplate.getCode())) {
            return ResponseEntity.ok(Response.failed(400, "模板code不能为空"));
        }
        //根据id获取模板
        simpleTemplate.setName(simpleTemplate.getName());
        simpleTemplate.setCode(simpleTemplate.getCode());
        crudService.update(simpleTemplate);
        return ResponseEntity.ok(Response.succeed("更新成功"));
    }

    @Override
    public ResponseEntity<Response<String>> addSimpleTemplate(SimpleTemplate simpleTemplate) {
        // TODO Auto-generated method stub
        if (StringUtils.isEmpty(simpleTemplate.getName())) {
            return ResponseEntity.ok(Response.failed(400, "模板名称不能为空"));
        }
        Long simpleTemplateId = crudService.save(simpleTemplate);
        if (simpleTemplateId != null) {
            return ResponseEntity.ok(Response.succeed("添加成功"));
        }
        return ResponseEntity.ok(Response.failed(400, "添加失败"));
    }

    @Override
    public ResponseEntity<Response<String>> addSimpleTemplateData(DomainRequest domainRequest) {
        // TODO Auto-generated method stub
        if (StringUtils.isEmpty(domainRequest.getCode())) {
            return ResponseEntity.ok(Response.failed(400, "模板要素code不能为空"));
        } else if (StringUtils.isEmpty(domainRequest.getLabel())) {
            return ResponseEntity.ok(Response.failed(400, "模板要素名称不能为空"));
        } else if (StringUtils.isEmpty(domainRequest.getKegex())) {
            return ResponseEntity.ok(Response.failed(400, "校验规则不能为空"));
        }
        String code = domainRequest.getCode().trim();
        List<Domain> domains = domainService.selectAll();
        boolean flag = false;
        for (Domain doamin : domains) {
            if (domainRequest.getCode().equals(doamin.getCode())) {
                flag = true;
            }
        }
        Long domainId;
        if (flag == false) {
            Domain domain = new Domain();
            domain.setCode(code);
            domain.setKegex(domainRequest.getKegex());
            domain.setType(domainRequest.getType());
            domain.setLabel(domainRequest.getLabel());
            domainId = domainService.add(domain);
        } else {
            Domain domain = crudService.uniqueResultHql(Domain.class, "from Domain where code = ?1", domainRequest.getCode());
            domainId = domain.getId();
        }

        if (domainId != null) {
            SimpleTemplateDomain simpleTemplateDomain = new SimpleTemplateDomain();
            SimpleTemplateDomain.ID id = new SimpleTemplateDomain.ID();
            id.setDomainId(domainId);
            id.setTemplateId(domainRequest.getTemplateId());
            simpleTemplateDomain.setId(id);
            crudService.save(simpleTemplateDomain);
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
        String code = domain.getCode().trim();
        domain.setCode(code);
        domainService.update(domain);
        return ResponseEntity.ok(Response.succeed("修改成功"));
    }

    @Override
    public ResponseEntity<Response<List>> TemplateList() {
        List<SimpleTemplate> simpleTemplates = crudService.hql(SimpleTemplate.class, "from SimpleTemplate");
        List list = new ArrayList();
        for (SimpleTemplate simpleTemplate : simpleTemplates) {
            Map map = new HashMap();
            map.put("code", simpleTemplate.getCode());
            map.put("name", simpleTemplate.getName());
            list.add(map);
        }
        return ResponseEntity.ok(Response.succeed(list));
    }

    @Override
    public ResponseEntity<Response<String>> deleteSimpleTemplateData(Long domainId, Long templateId) {
        if (domainId == null) {
            return ResponseEntity.ok(Response.failed(400, "要素id不能为空"));
        }
        Domain domain = domainService.selectById(domainId);
        if (domain.getCode().equals("idCard")) {
            return ResponseEntity.ok(Response.failed(400, "核心字段不能删除"));
        }
        domainService.deleteById(domainId);
        SimpleTemplateDomain simpleTemplateDomain = new SimpleTemplateDomain();
        SimpleTemplateDomain.ID id = new SimpleTemplateDomain.ID();
        id.setTemplateId(templateId);
        id.setDomainId(domainId);
        simpleTemplateDomain.setId(id);
        crudService.delete(simpleTemplateDomain);
        return ResponseEntity.ok(Response.succeed("删除成功"));
    }

    @Override
    public ResponseEntity<Response<String>> updateTemplateState(Long templateId) {
        if (templateId == null) {
            return ResponseEntity.ok(Response.succeed("模板id不能为空"));
        }
        Template template = templateService.getTemplate(templateId);
        if (template.getState() == 0) {
            template.setState(1);
        } else if (template.getState() == 1) {
            template.setState(0);
        }
        templateService.update(template);
        return ResponseEntity.ok(Response.succeed("更改成功"));
    }
}
