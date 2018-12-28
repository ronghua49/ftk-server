package com.risepu.ftk.web.b.controller;

import com.risepu.ftk.server.domain.*;
import com.risepu.ftk.server.service.*;
import com.risepu.ftk.utils.ChartGraphics;
import com.risepu.ftk.web.Constant;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.m.dto.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private PdfService pdfService;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private DomainService domainService;

    @Autowired
    private QrCodeUtilSerevice qrCodeUtilSerevice;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private ChainService chainService;

    @Value("${ftk.qrcode.urlPrefix}")
    private String urlPrefix;

    @Value("${ftk.root.filePath}")
    private String filePath;

    private Integer t = 0;

    @Override
    public ResponseEntity<Response<String>> add(Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Request Uri: /documentData/add");
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
            String date = ft.format(new Date());

            File file = new File(filePath + date);
            file.mkdirs();
            Long templateId = Long.parseLong(map.get("templateId"));
            // 根据模板id得到模板
            Template template = templateService.getTemplate(templateId);

            // 根据模板id得到模板数据
            List<Domain> list = domainService.selectByTemplate(templateId);

            OrganizationUser organizationUser = (OrganizationUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
            OrganizationUser user = organizationService.findOrgUserById(organizationUser.getId());

            Organization org = organizationService.findAuthenOrgById(user.getOrganizationId());

            //生成盖章图片
            ChartGraphics cg = new ChartGraphics();
            String GrFilePath = cg.graphicsGeneration(org.getName(), filePath + date + "/" + org.getId() + "(" + t++ + ").jpg");


            //pdf流输出路径
            String pdfFilePath = filePath + date + "/职场通行证-" + template.getName() + "-" + date + "(" + t++ + ").pdf";

            ProofDocument proofDocument = new ProofDocument();
            proofDocument.setPersonalUser(map.get("idCard"));
            proofDocument.setOrganization(org.getId());
            proofDocument.setTemplate(templateId);
            Long proDocumentId = proofDocumentService.add(proofDocument);
            ProofDocument proofDocument1 = proofDocumentService.getDocumentById(proDocumentId);
            String hash = chainService.sign(proDocumentId);
            //生成二维码图片
            String qrFilePath = qrCodeUtilSerevice.createQrCode(filePath + date + "/" + map.get("idCard") + "(" + t++ + ").jpg", urlPrefix + hash);

            // 文档保存路径
            String filePath = pdfService.pdf(map, hash, qrFilePath, GrFilePath, pdfFilePath);
            proofDocument1.setChainHash(hash);
            proofDocument1.setFilePath(filePath);
            proofDocumentService.updateDocument(proofDocument1);
            if (proDocumentId != null) {
                for (int i = 0; i < list.size(); i++) {
                    documentDateService.add(list.get(i).getId(), proDocumentId, map.get(list.get(i).getCode()));
                }
            }
            return ResponseEntity.ok(Response.succeed(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Response.failed(400, ""));
        }
    }

    @Override
    public ResponseEntity<Response<String>> sendEmail(@RequestBody EmailRequest emailRequest) {
        // TODO Auto-generated method stub
        logger.debug("Request Uri: /documentData/sendEmail");
        try {
            sendMailService.sendMail(emailRequest.getEmail(), emailRequest.getFilePath());
            return ResponseEntity.ok(Response.succeed("邮件发送成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(Response.failed(400, "邮件发送失败"));
        }
    }
}
