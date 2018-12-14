/**
 *
 */
package com.risepu.ftk.web.b.controller;

import static org.mockito.Matchers.intThat;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.utils.PageResult;
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
//		for(int i=0;i<20;i++) {
//			Organization organization = new Organization();
//			organization.setId("133"+i);
//			organization.setName("阿里巴巴"+i);
//			crudService.save(organization);
//			System.out.println("hellow ");
//		}
//		return "scuess";
		
		//Organization organization = crudService.uniqueResultByProperty(Organization.class, "id", "1330");
		PageResult<Organization> pageResult = new PageResult<>();
		
		int pageNo = 1;
		int pageSize=5;
		int firstIndex = (pageNo-1)*pageSize;
		List<Organization> orgList = crudService.hql(Organization.class,firstIndex, pageSize, "from Organization where name like ?1 order by createTimestamp desc","%"+"阿里"+"%");
		pageResult.setCount(orgList.size());
		pageResult.setData(orgList);
		return pageResult;
		
	}
	
}
