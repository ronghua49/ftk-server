package com.risepu.ftk.web.b.controller;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.service.PdfService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.utils.PdfReplacer;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.RegistResult;

import net.lc4ever.framework.service.GenericCrudService;

/**
 * @author q-wang
 */
@Controller
@RequestMapping(path = "/api/sample")
public class SampleController implements SampleApi {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PdfService pdfService;

	@Override
	public ResponseEntity<Response<String>> add(Long templateId, String _template, HttpServletResponse response) throws Exception {
		PdfReplacer textReplacer = new PdfReplacer("C:/Users/MACHEMIKE/Desktop/一次模板V1.1.pdf");
		textReplacer.replaceText("${_template}", _template);
		textReplacer.replaceText("${_title}", "小白撒反对大师傅但是放松放松放松");
		textReplacer.replaceText("${{_chainHash}", "小白撒反对大师傅但是放松放松放松");
		textReplacer.toPdf("C:/Users/MACHEMIKE/Desktop/测试.pdf");
		return ResponseEntity.ok(Response.succeed("生成文档成功"));
	}

	@Autowired
	private GenericCrudService crudService;

	@Override
	public ResponseEntity<Response<RegistResult>> regist() {

		logger.debug("Request Uri: /sample/sample1");
		Response<List<String>> sample1 = Response.succeed(Arrays.asList(new String[] { "hello", "world" }));
		RegistResult result = new RegistResult();

		return ResponseEntity.ok(Response.succeed(result));
	}

	@GetMapping("/test")
	public PageResult<Organization> saveOrg() {
		// for(int i=0;i<20;i++) {
		// Organization organization = new Organization();
		// organization.setId("133"+i);
		// organization.setName("阿里巴巴"+i);
		// crudService.save(organization);
		// System.out.println("hellow ");
		// }
		// return "scuess";

		// Organization organization =
		// crudService.uniqueResultByProperty(Organization.class, "id", "1330");
		PageResult<Organization> pageResult = new PageResult<>();

		int pageNo = 1;
		int pageSize = 5;
		int firstIndex = (pageNo - 1) * pageSize;
		List<Organization> orgList = crudService.hql(Organization.class, firstIndex, pageSize, "from Organization where name like ?1 order by createTimestamp desc", "%" + "阿里" + "%");
		return pageResult;

	}

	@GetMapping("/test1")
	@ResponseBody
	public String get() {
		return "success get";
	}

	@PostMapping("/test2")
	@ResponseBody
	public String test(@RequestParam String test2) {
		return test2;
	}

	@GetMapping("/test3")
	@ResponseBody
	public List<Organization> testObject() throws ParseException {
		//多参数的查询
		//string转换data的问题
		//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mmm:ss");
		//		Date date2 = format.parse("2018-12-13 13:37:37");
		Object[] params = { "13310" };

		List<Organization> orgs = crudService.hql(Organization.class, 0, 5, "from  Organization where id <?1", params);
		System.out.println(orgs.size());

		//OrganizationUser org = crudService.uniqueResultByProperty(OrganizationUser.class, "id", "135123835407");
		return orgs;
	}

}
