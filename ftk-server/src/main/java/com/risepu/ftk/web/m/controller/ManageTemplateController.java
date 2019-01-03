package com.risepu.ftk.web.m.controller;

import com.risepu.ftk.server.domain.Domain;
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
    public ResponseEntity<Response<PageResult>> getAnyDomain(Integer pageNo, Integer pageSize, String code, String label, Long templateId) {
        Integer firstIndex = pageNo * pageSize;
        domainService.selectByTemplate(templateId);
        String hql = "from Domain d where d.id in (select t.id.domainId from TemplateDomain t where t.id.templateId = " + templateId + ")";
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
    public ResponseEntity<Response<List<Domain>>> getAllDomain() {
        List<Domain> domains = domainService.selectAll();
        return ResponseEntity.ok(Response.succeed(domains));
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

            String _template = template.get_template();
            List<Domain> domains = domainService.selectAll();
            List<String> list1 = new ArrayList();
            for (int i = 0; i < domains.size(); i++) {
                Domain domain = domains.get(i);
                list1.add(domain.getCode());
                String code = "${" + domain.getCode() + "}";
                if (_template.contains(code)) {
                    _template = _template.replace(code, "____");
                    templateDomainService.add(template.getId(), domain.getId());
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
                    crudService.delete(templateService.getTemplate(template.getId()));
                    return ResponseEntity.ok(Response.failed(400, "参数错误，请仔细检查！"));
                }
            }
            ChartGraphics cg = new ChartGraphics();
            String GrFilePath = cg.graphicsGeneration("******有限公司", filePath + date + "/示例盖章.jpg");
            String pdfFilePath = filePath + date + "/" + template.getId() + "（" + t++ + ").pdf";
            String qrFilePath = qrCodeUtilSerevice.createQrCode(filePath + date + "/示例二维码.jpg", "china is good");
            String filePath1 = pdfService.pdf(_template, "97481fb743487be151082fde934762eb9e3366a3", template.getName(), qrFilePath, GrFilePath, pdfFilePath);
            template1.set_template(template.get_template());
            template1.setDescription(template.getDescription());
            template1.setName(template.getName());
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
    public ResponseEntity<Response<String>> updateTemplateName(Template template) {
        if (template.getId() == null) {
            return ResponseEntity.ok(Response.failed(400, "模板id不能为空"));
        }
        if (StringUtils.isEmpty(template.getName())) {
            return ResponseEntity.ok(Response.failed(400, "模板名称不能为空"));
        }
        if (StringUtils.isEmpty(template.getCode())) {
            return ResponseEntity.ok(Response.failed(400, "模板code不能为空"));
        }
        //根据id获取模板
        Template template1 = templateService.getTemplate(template.getId());
        template1.setName(template.getName());
        template1.setCode(template.getCode());
        templateService.update(template1);
        return ResponseEntity.ok(Response.succeed("更新成功"));
    }

    @Override
    public ResponseEntity<Response<String>> addTemplate(@RequestBody Template template) {
        // TODO Auto-generated method stub
        if (StringUtils.isEmpty(template.getName())) {
            return ResponseEntity.ok(Response.failed(400, "模板名称不能为空"));
        }
        Long templateId = templateService.add(template);
        if (templateId != null) {
            return ResponseEntity.ok(Response.succeed("添加成功"));
        }
        return ResponseEntity.ok(Response.failed(400, "添加失败"));
    }

    @Override
    public ResponseEntity<Response<String>> addTemplateData(DomainRequest domainRequest) {
        // TODO Auto-generated method stub
        if (StringUtils.isEmpty(domainRequest.getCode())) {
            return ResponseEntity.ok(Response.failed(400, "模板要素code不能为空"));
        } else if (StringUtils.isEmpty(domainRequest.getLabel())) {
            return ResponseEntity.ok(Response.failed(400, "模板要素名称不能为空"));
        } else if (StringUtils.isEmpty(domainRequest.getKegex())) {
            return ResponseEntity.ok(Response.failed(400, "校验规则不能为空"));
        }
        String code = domainRequest.getCode().trim();
        Domain domain = new Domain();
        domain.setCode(code);
        domain.setKegex(domainRequest.getKegex());
        domain.setType(domainRequest.getType());
        domain.setLabel(domainRequest.getLabel());
        Long domainId = domainService.add(domain);
        if (domainId != null) {
            templateDomainService.add(domainRequest.getTemplateId(), domainId);
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
        List<Template> templates = templateService.getAllTemplate("from Template");
        List list = new ArrayList();
        for (Template template : templates) {
            Map map = new HashMap();
            map.put("code", template.getId());
            map.put("name", template.getName());
            list.add(map);
        }
        return ResponseEntity.ok(Response.succeed(list));
    }

    @Override
    public ResponseEntity<Response<String>> deleteTemplateData(Long domainId, Long templateId) {
        if (domainId == null) {
            return ResponseEntity.ok(Response.failed(400, "要素id不能为空"));
        }
        Domain domain = domainService.selectById(domainId);
        if (domain.getCode().equals("idCard")) {
            return ResponseEntity.ok(Response.failed(400, "核心字段不能删除"));
        }
        domainService.deleteById(domainId);
        templateDomainService.delete(templateId, domainId);
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
