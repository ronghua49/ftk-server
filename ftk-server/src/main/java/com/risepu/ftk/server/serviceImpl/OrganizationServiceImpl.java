package com.risepu.ftk.server.serviceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.server.service.OrganizationService;

import net.lc4ever.framework.service.GenericCrudService;

/**
 * 
 * @author L-heng
 *
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private GenericCrudService crudService;

	public void setCrudService(GenericCrudService crudService) {
		this.crudService = crudService;
	}

	@Override
	public String orgReg(String phone, String code, String password) {
		// 判断code和当前code一致否
		if (code.equals("123123")) {
			// 入库
			Organization org = new Organization();
			org.setPhone(phone);
			org.setPassword(password);
			crudService.save(org);
			return "注册成功！";
		} else {
			return "验证码输入有误！";
		}
	}

	@Override
	public String orgLogin(String phoneOrName, String password) {
		// 判断输入的类型
		if (StringUtils.isNumeric(phoneOrName)) {
			// 判断该用户是否存在
			Organization org = crudService.uniqueResultByProperty(Organization.class, "phone", phoneOrName);
			if (org != null && org.getPassword().equals(password)) {
				// 登录成功
				return "登录成功";
			} else {
				return "手机号或者密码错误！";
			}
		} else {
			OrganizationUser org = crudService.uniqueResultByProperty(OrganizationUser.class, "organization",
					phoneOrName);
			if (org != null && org.getPassword().equals(password)) {
				// 登录成功
				return "登录成功";
			} else {
				return "企业名或者密码错误！";
			}

		}
	}

}
