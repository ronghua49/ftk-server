package com.risepu.ftk.web.b.controller;

import com.risepu.ftk.server.domain.*;
import com.risepu.ftk.server.service.*;
import com.risepu.ftk.utils.ChartGraphics;
import com.risepu.ftk.web.Constant;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.m.dto.EmailRequest;
import net.lc4ever.framework.format.DateFormatter;
import net.lc4ever.framework.service.GenericCrudService;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Autowired
    private GenericCrudService crudService;

    @Value("${ftk.qrcode.urlPrefix}")
    private String urlPrefix;

    @Value("${ftk.root.filePath}")
    private String filePath;

    @Value("${ftk.root.filePaths}")
    private String filePaths;

    //FIXME:线程安全 企业单号每天从1开始递增
    private Integer t = 1;

    @Override
    public ResponseEntity<Response<ProofDocument>> addProofDocument(Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Request Uri: /documentData/add");
        try {
            //格式化时间，精确到毫秒，用于防止生成pdf文件名相同
            SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmssSS");
            String date = ft.format(new Date());

            //格式化时间，用于创建2层文件夹
            SimpleDateFormat ft1 = new SimpleDateFormat("yyyy-MM/dd");
            String date1 = ft1.format(new Date());

            SimpleDateFormat ft3 = new SimpleDateFormat("yyyyMMdd");
            String date3 = ft3.format(new Date());

            //检测文件夹是否存在，若不存在，创建
            File file = new File(filePath + date1);
            if (!file.exists()) {
                file.mkdirs();
            }
            //把templateId转为long类型
            Long templateId = Long.parseLong(map.get("templateId"));
            // 根据模板id得到模板
            Template template = templateService.getTemplate(templateId);

            // 根据模板id得到模板数据
            List<Domain> list = domainService.selectByTemplate(templateId);

            //从session得到目前登陆的用户信息
            OrganizationUser organizationUser = (OrganizationUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
            //根据用户信息id得到最新的用户信息
            OrganizationUser user = organizationService.findOrgUserById(organizationUser.getId());
            //得到目前登陆的用户所在的企业信息
            Organization org = organizationService.findAuthenOrgById(user.getOrganizationId());

            //生成盖章图片
            ChartGraphics cg = new ChartGraphics();
            String GrFilePath = cg.graphicsGeneration(org.getName(), filePath + date1 + "/" + org.getId() + date + ".jpg");

            //pdf流输出路径
            String pdfFilePath = filePath + date1 + "/职场通行证-" + template.getName() + "-" + date + ".pdf";

            Long proofDocumentId = Long.parseLong(map.get("proofDocumentId"));
            ProofDocument proofDocument = proofDocumentService.getDocumentById(proofDocumentId);
            proofDocument.setPersonalUser(map.get("idCard"));
            proofDocument.setOrganization(org.getId());
            proofDocument.setTemplate(templateId);
            proofDocument.setIndex(0);
            proofDocumentService.updateDocument(proofDocument);

            ProofDocument proofDocument1 = proofDocumentService.getDocumentById(proofDocumentId);

            //得到模板名称的拼音首字母
            String title = template.getName();
            String convert = "";
            for (int j = 0; j < title.length(); j++) {
                char word = title.charAt(j);
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
                if (pinyinArray != null) {
                    convert += pinyinArray[0].charAt(0);
                } else {
                    convert += word;
                }
            }
            //查询所有文档
            List<ProofDocument> list1 = crudService.hql(ProofDocument.class, "from ProofDocument");
            //用于保存文档的创建时间
            List<String> dateList = new ArrayList<>();
            //用于保存创建文档的企业id
            List<String> organizationList = new ArrayList<>();
            for (int i = 0; i < list1.size(); i++) {
                dateList.add(ft3.format(list1.get(i).getCreateTimestamp()));
                organizationList.add(list1.get(i).getOrganization());
            }

            String n = "";
            if (dateList.contains(date3) && organizationList.contains(user.getOrganizationId())) {
                SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd");
                Date date2 = DateFormatter.startOfDay(DateFormatter.nextDay(new Date()));
                String time = ft2.format(date2);
                String time1 = ft2.format(new Date());
                Integer index = (Integer) crudService.uniqueResultHql("select max(index) from ProofDocument where organization = ?1 and createTimestamp >= '" + time1 + "' and createTimestamp < '" + time + "'", user.getOrganizationId());
                proofDocument1.setIndex(index + 1);
                n = String.format("%03d", index + 1);
            } else {
                proofDocument1.setIndex(1);
                n = String.format("%03d", proofDocument1.getIndex());
            }
            String number = "ZKTXZ-" + convert.toUpperCase() + "-" + date3 + n;

            String hash = chainService.sign(proofDocumentId);

            //生成二维码图片
            String qrFilePath = qrCodeUtilSerevice.createQrCode(filePath + date1 + "/" + map.get("idCard") + date + ".jpg", urlPrefix + hash);

            // 文档保存路径
            String filePath = pdfService.pdf(map, hash, qrFilePath, GrFilePath, pdfFilePath);
            proofDocument1.setChainHash(hash);
            proofDocument1.setNumber(number);
            proofDocument1.setState(0);
            proofDocument1.setFilePath(filePath);
            proofDocumentService.updateDocument(proofDocument1);
            if (proofDocumentId != null) {
                for (int i = 0; i < list.size(); i++) {
                    documentDateService.add(list.get(i).getId(), proofDocumentId, map.get(list.get(i).getCode()));
                }
            }
            return ResponseEntity.ok(Response.succeed(proofDocument1));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Response.failed(400, ""));
        }
    }

    @Override
    public ResponseEntity<Response<ProofDocument>> add(Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmssSS");
            SimpleDateFormat ft1 = new SimpleDateFormat("yyyy-MM-dd");
            String date = ft.format(new Date());
            String date1 = ft1.format(new Date());

            File file = new File(filePaths + date1);
            if (!file.exists()) {
                file.mkdirs();
            }
            Long templateId = Long.parseLong(map.get("templateId"));
            // 根据模板id得到模板
            Template template = templateService.getTemplate(templateId);

            OrganizationUser organizationUser = (OrganizationUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
            OrganizationUser user = organizationService.findOrgUserById(organizationUser.getId());
            Organization org = organizationService.findAuthenOrgById(user.getOrganizationId());

            //生成盖章图片
            ChartGraphics cg = new ChartGraphics();
            String GrFilePath = cg.graphicsGeneration(org.getName(), filePaths + date1 + "/" + org.getId() + date + ".jpg");

            //pdf流输出路径
            String pdfFilePath = filePaths + date1 + "/职场通行证-" + template.getName() + "-" + date + ".pdf";

            String hash = "97481fb743487be151082fde934762eb9e3366a3";

            //生成二维码图片
            String qrFilePath = qrCodeUtilSerevice.createQrCode(filePaths + date1 + "/" + map.get("idCard") + date + ".jpg", urlPrefix + hash);

            // 文档保存路径
            String filePath = pdfService.pdf(map, hash, qrFilePath, GrFilePath, pdfFilePath);
            ProofDocument proofDocument = new ProofDocument();
            proofDocument.setState(1);
            proofDocument.setFilePath(filePath);
            Long id = proofDocumentService.add(proofDocument);
            if (id != null) {
                return ResponseEntity.ok(Response.succeed(proofDocumentService.getDocumentById(id)));
            } else {
                return ResponseEntity.ok(Response.failed(400, "生成失败咯！"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Response.failed(400, "生成失败"));
        }
    }

    @Override
    public ResponseEntity<Response<String>> sendEmail(EmailRequest emailRequest) {
        // TODO Auto-generated method stub
        logger.debug("Request Uri: /documentData/sendEmail");
        try {
            Template template = templateService.getTemplate(emailRequest.getTemplateId());
            sendMailService.sendMail(emailRequest.getEmail(), emailRequest.getFilePath(), template.getName());
            return ResponseEntity.ok(Response.succeed("邮件发送成功"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.ok(Response.failed(400, "邮件发送失败"));
        }
    }
}
