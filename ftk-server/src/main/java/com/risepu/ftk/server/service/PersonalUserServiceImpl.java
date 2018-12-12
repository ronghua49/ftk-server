package com.risepu.ftk.server.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.risepu.ftk.server.domain.PersonalUser;

import net.lc4ever.framework.service.GenericCrudService;

@Service
public class PersonalUserServiceImpl implements PersonalUserService {

	@Autowired
	private GenericCrudService crudService;

	@Override
	/**
	 * 验证码注册
	 */
	public String personReg(String phone, String code, String password) {

		// 从session 获取code
		if (code != null) {
			if ("123122".equals(code)) {
				//用户入库
				PersonalUser user = new PersonalUser();
				user.setMobile(phone);
				user.setPassword(password);
				crudService.save(user);
				return "注册成功！";
			} else {

				return "验证码输入错误";
			}

		} else {
			return "验证码已过期，请重新获取！";
		}

	}

	@Override
	public String personLoginUsePwd(String phone, String password) {
		PersonalUser personalUser = crudService.uniqueResultByProperty(PersonalUser.class, "phone", phone);
		if (personalUser != null) {
			if (personalUser.getPassword().equals(password)) {
				//存入用户到当前session
				
				
				return "登录成功";
			} else {
				return "密码错误";
			}

		} else {
			return "您还没有注册，请注册后登录";
		}
	}

	@Override
	/**
	 * 验证码登录
	 */
	public String personLoginUseCode(String phone, String code) {
		//判断用户输入的code和阿里云 session code 是否一致
		
		if("123123".equals(code)) {
			
			
		}
		return null;
	}

}
