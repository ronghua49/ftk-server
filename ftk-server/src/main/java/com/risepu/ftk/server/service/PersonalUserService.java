package com.risepu.ftk.server.service;

import java.util.Map;

import com.risepu.ftk.server.domain.AuthorizationStream;
import com.risepu.ftk.server.domain.PersonalUser;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.p.dto.AuthHistoryInfo;
import com.risepu.ftk.web.p.dto.LoginRequest;

import net.lc4ever.framework.remote.annotation.Remote;

@Remote(path="/person")
public interface PersonalUserService {
	/**
	 * 个人用户注册
	 * @param phone 电话
	 * @param cardNo 身份证号
	 * @param userName 用户名
	 * @return 
	 */
	public String personReg(String mobile,String cardNo,String userName);
	
	/**
	 * 用户通过验证码登录
	 * @param cardNo 用户身份证号
	 * @param phone 手机号
	 * @return
	 */
	public PersonalUser personLogin(String cardNo, String phone);

	/**
	 * 保存授权操作流水记录
	 * @param authStream
	 */
	public void saveAuthStream(AuthorizationStream authStream);

	/**
	 * 查询授权历史记录
	 * @param key
	 * @param pageNo 查询的页码
	 * @param pageSize 每页显示几条数据
	 * @return 查询页面
	 */
	public PageResult<AuthHistoryInfo> queryHistoryByParam(Map<String, Object> paraMap, Integer pageNo, Integer pageSize);

	
}
