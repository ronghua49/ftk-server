package com.risepu.ftk.web.b.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.risepu.ftk.server.service.DocumentDateService;
import com.risepu.ftk.server.service.ProofDocumentService;

@Controller
@RequestMapping("/documentData")
public class DocumentDataController {
	private ProofDocumentService proofDocumentService;

	private DocumentDateService documentDateService;

	@RequestMapping
	public void add(String name, String idCard, String job, String date, String adress, Long template, String chainHash,
			String organization, JsonObject json) {

		// 文档数据
		Map<Long, String> map = new HashMap<>();

//		for (Entry<String, JsonElement> a : json.entrySet()) {
//			String b = a.getKey();
//			Long c = Long.parseLong(b);
//			map.put(c, a.getValue().getAsString());
//		}

		if (name != null) {
			map.put(1l, name);
		}
		if (idCard != null) {
			map.put(2l, idCard);
		}
		if (job != null) {
			map.put(3l, job);
		}
		if (date != null) {
			map.put(4l, date);
		}
		if (adress != null) {
			map.put(5l, adress);
		}
		// 添加文档
		Long documentId = proofDocumentService.add(template, chainHash, organization, idCard);
		// 添加文档数据
		for (Entry<Long, String> entry : map.entrySet()) {
			documentDateService.add(entry.getKey(), documentId, entry.getValue());
		}
	}

}
