package com.risepu.ftk.web.b.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.risepu.ftk.server.service.*;
import com.risepu.ftk.server.serviceImpl.QrCodeUtilServiceImpl;
import com.risepu.ftk.utils.ChartGraphics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.ProofDocument;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.web.api.Response;

import javax.servlet.http.HttpServletResponse;

/**
 * @author L-heng
 */
@Controller
@RequestMapping("/api/documentData")
public class DocumentDataController implements DocumentDataApi {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProofDocumentService proofDocumentService;

    @Autowired
    private DocumentDateService documentDateService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private DomainService domainService;

    @Autowired
    private QrCodeUtilSerevice qrCodeUtilSerevice;

    @Override
    public ResponseEntity<Response<String>> add(Map<String, String> map, HttpServletResponse response) throws Exception {
        logger.debug("Request Uri: /documentData/add");
        File file = new File("/file-path");
        file.mkdirs();
        Long templateId = Long.parseLong(map.get("templateId"));
        // 根据模板id得到模板数据
        List<Domain> list = domainService.selectByTemplate(templateId);
        // 根据模板id得到模板
        Template template = templateService.getTemplate(templateId);
        // 获取一次模板
        String _template = template.get_template();

        List<Domain> list2 = new ArrayList<>();
        // 替换一次模板中的数据
        for (int i = 0; i < list.size(); i++) {
            Domain domain = list.get(i);
            Domain domain1 = domainService.selectByCode(domain.getCode());
            if (domain1 != null) {
                list2.add(domain1);
            } else {
                return ResponseEntity.ok(Response.succeed("参数错误"));
            }
            String key = "${" + domain.getCode() + "}";
            String value = map.get(domain.getCode());
            _template = _template.replace(key, value);
        }
        //生成二维码图片
        qrCodeUtilSerevice.createQrCode("/file-path/Person.jpg", "china is good");

        //生成盖章图片
        ChartGraphics cg = new ChartGraphics();
        cg.graphicsGeneration("北京声谱科技有限公司", "/file-path/Organization.jpg");
        String hash = "SGDHHFSGFSGFSGFS";
        String title = map.get("title");
        // 文档保存路径
        String filePath = pdfService.pdf(_template, hash, "单据证明");

        ProofDocument proofDocument = new ProofDocument();
        proofDocument.setFilePath(filePath);
        proofDocument.setPersonalUser(map.get("idCard"));
        proofDocument.setTemplate(templateId);
        Long proDocumentId = proofDocumentService.add(proofDocument);
        if (proDocumentId != null) {
            for (int i = 0; i < list2.size(); i++) {
                documentDateService.add(list2.get(i).getId(), proDocumentId, map.get(list2.get(i).getCode()));
            }
        }
        return ResponseEntity.ok(Response.succeed("文档添加成功"));
    }

    @Override
    public ResponseEntity<Response<String>> sendEmail(String email) throws Exception {
        // TODO Auto-generated method stub
        logger.debug("Request Uri: /documentData/sendEmail");
        sendMailService.sendMail(email);
        return ResponseEntity.ok(Response.succeed("邮件发送成功"));
    }

}
