package com.risepu.ftk.server.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationUser;

import net.lc4ever.framework.service.GenericCrudService;

@Service
public class OrganizationServiceImpl implements OrganizationService{
	
	private static final String SALT = "$%)(P123";
	
	@Autowired
	private GenericCrudService crudService;

	public void setCrudService(GenericCrudService crudService) {
		this.crudService = crudService;
	}

	@Override
	public String orgReg(String phone, String code, String password) {
		//判断code和当前code一致否
		if(code.equals("123123")) {
			//入库
			OrganizationUser org = new OrganizationUser();
			org.setId(phone);
			password= DigestUtils.md5Hex(password+SALT);
			org.setPassword(password);
			crudService.save(org);
			return "注册成功！";
		}else {
			return "验证码输入有误！";
		}
	}

	@Override
	public Map<String, Object> orgLogin(String phoneOrName, String password) {
		password= DigestUtils.md5Hex(password+SALT);
		Map<String, Object> loginResult = new HashMap<String, Object>();
		//判断输入的类型
		if(StringUtils.isNumeric(phoneOrName)) {
			//判断企业用户是否存在
			OrganizationUser org = crudService.uniqueResultByProperty(OrganizationUser.class, "id", phoneOrName);
			if(org!=null && org.getPassword().equals(password)) {
				//登录成功
				loginResult.put("message", "登录成功！");
				loginResult.put("org", org);
			}else {
				loginResult.put("message", "手机号或者密码错误！");
			}
		}else {
			//使用企业名登录
			Organization org = crudService.uniqueResultByProperty(Organization.class, "name",phoneOrName);
			
			if(org!=null) {
				OrganizationUser orgUser = crudService.uniqueResultByProperty(OrganizationUser.class, "id", org.getId());
				if(orgUser.getPassword().equals(password)) {
					//登录成功
					loginResult.put("message", "登录成功！");
					loginResult.put("org", org);
				}
				
			}else {
				loginResult.put("message", "企业名或者密码错误！");
			}
			
		}
		
		return loginResult;
		
	}

	/*在审核验证码通过后执行*/
	@Override
	public String changePwd(String phone, String newPwd) {
		OrganizationUser orgUser = new OrganizationUser();
		orgUser.setId(phone);
		orgUser.setPassword(newPwd);
		crudService.update(orgUser);
		return "success";
	}
	

	

}
