package com.risepu.ftk.server.service;

import java.util.Map;

import com.risepu.ftk.server.domain.AuthorizationStream;
import com.risepu.ftk.server.domain.PersonalUser;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.p.dto.AuthHistoryInfo;

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
	public void update(AuthorizationStream authStream);

	/**
	 * 查询授权历史记录
	 * @param key
	 * @param pageNo 查询的页码
	 * @param pageSize 每页显示几条数据
	 *  @param personId 当前用户id
	 * @return 查询页面
	 */
	public PageResult<AuthHistoryInfo> queryHistoryByParam(String key, Integer pageNo, Integer pageSize,String personId);

	/**
	 * 根据用户身份证查询请求认证的企业名
	 * @param cardNo 身份证号
	 * @return 需求授权的企业名称    流水的
	 */
	public Map<String, Object> findNewRequestByCardNo(String cardNo);

	
	/**
	 * 根据流水id查询 流水
	 * @param streamId 授权流水id
	 * @return
	 */
	public AuthorizationStream findAuthorizationStreamById(Integer streamId);

	
}
