package com.risepu.ftk.web.b.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.server.domain.PersonalUser;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.web.api.Response;

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
	
	

	@GetMapping("/authen")
	public Response<String> orgAuthen(@RequestBody PersonalUser personalUser,HttpServletRequest request){
		
		OrganizationUser  user = (OrganizationUser) request.getSession().getAttribute("org");
		//organizationService.orgAuthenInfo(personalUser);
		
		return Response.succeed("success");
		
		
	}
	
	

}
