package com.risepu.ftk.web.b.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.DocumentDateService;
import com.risepu.ftk.server.service.PdfService;
import com.risepu.ftk.server.service.ProofDocumentService;
import com.risepu.ftk.server.service.SendMailService;
import com.risepu.ftk.server.service.TemplateService;
import com.risepu.ftk.web.api.Response;

/**
 * 
 * @author L-heng
 *
 */
@Controller
@RequestMapping("/api/documentData")
public class DocumentDataController implements DocumentDataApi {

	@Autowired
	private ProofDocumentService proofDocumentService;

	@Autowired
	private DocumentDateService documentDateService;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private PdfService pdfService;

	@Autowired
	private SendMailService SendMailService;

	@Override
	public ResponseEntity<Response<String>> add(Long templateId) throws Exception {
		pdfService.pdf(templateId);
		// 文档文字类数据
//		Map<String, String> map = new HashMap<>();
//
//		for (Entry<String, JsonElement> a : json.entrySet()) {
//			String b = a.getKey();
//			map.put(b, a.getValue().getAsString());
//		}
		// 根据模板id得到模板
//		Template template=templateService.getTemplate(templateId);
		return ResponseEntity.ok(Response.succeed("生成pdf成功"));

		// 获取一次模板
//		String _template=template.get_template();
		// 生成二维码

		// 接口返回hash
//		String chainHash = "";
//		String organization = "";

		// 生成文档
		// 模板路径
		// String templatePath = templateService.getFilePath(template);
		// 文档路径
//		String newPDFPath = "";

		// 文档文字类数据
//		Map<String, String> imgmap = new HashMap<>();
//		imgmap.put("二维码路径", "");
//		Organization orga = (Organization) request.getAttribute("公司");
//		imgmap.put("公司盖章路径", "");

		// Map<String, Map<String, String>> map = new HashMap<>();
		// map.put("datemap", datemap);
		// map.put("imgmap", imgmap);

		// pdfService.pdf(templatePath, newPDFPath, map);
		// 添加文档
		// Long documentId = proofDocumentService.add(template, chainHash, organization,
		// datemap.get("身份证号"));
		// 添加文档数据
//		for (Entry<String, String> entry : datemap.entrySet()) {
//			Long key = Long.parseLong(entry.getKey());
//			documentDateService.add(key, documentId, entry.getValue());
//		}

		// return 文档地址
	}

	@Override
	public ResponseEntity<Response<String>> sendEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		SendMailService.sendMail(email);
		return ResponseEntity.ok(Response.succeed("邮件发送成功"));
	}

}
