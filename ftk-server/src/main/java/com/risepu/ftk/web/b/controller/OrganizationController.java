package com.risepu.ftk.web.b.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.server.domain.PersonalUser;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.server.service.SmsService;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.RegistResult;
import com.risepu.ftk.web.b.vo.RegistVo;

@RestController
@RequestMapping(name="/org")
public class OrganizationController implements Controller {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private OrganizationService organizationService;
	
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}
	
	/**
	 * 企业端注册
	 * @return
	 */
	@GetMapping("/regist")
	public ResponseEntity<Response<RegistResult>> orgRegist(@RequestBody RegistVo registVo,HttpServletRequest request){
	
		//判断smsCode
		RegistResult registResult = new RegistResult();
		
		if(registVo.getMsmCode().equals(request.getSession().getAttribute("smsCode"))) {
			organizationService.orgReg(registVo.getMobile(), registVo.getPassword());
			registResult.setMessage("注册成功！");
		}else {
			registResult.setMessage("验证码输入有误！");
		}
		
		return ResponseEntity.ok(Response.succeed(registResult));
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<Response<LoginResult>> orgLogin(@RequestParam(name="name") String mobileOrName,@RequestParam String password){
		
		Map<String, Object> regResultMap = organizationService.orgLogin(mobileOrName, password);
		
		
		
	}
	
	

	@GetMapping("/authen")
	public Response<String> orgAuthen(@RequestBody PersonalUser personalUser,HttpServletRequest request){
		
		OrganizationUser  user = (OrganizationUser) request.getSession().getAttribute("org");
		//organizationService.orgAuthenInfo(personalUser);
		
		return Response.succeed("success");
		
		
	}
	
	

}
