package com.risepu.ftk.server.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationAdvice;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.b.dto.LoginResult;

//@Remote(path="/org")
public interface OrganizationService {
	
	 /**
	  * 
	  * @param phone 手机号
	  * @param password 密码
	  * @return
	  */
	public String orgReg(String phone,String password);
	
	/**
	 *  
	 * @param phoneOrName 
	 * @param password
	 * @return LoginResult 登录返回结果
	 * 
	 */
	public LoginResult orgLogin(String phoneOrName,String password); 
	
	/**
	 * 
	 * @param id 手机号 获得 组织机构代码号
	 * @param newPwd 新密码
	 */
	public void changePwd(String id,String newPwd);
	
	
	/**
	 * 上传图片
	 * @param file
	 * @return 图片的存储名
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public String upload (MultipartFile file) throws IllegalStateException, IOException;
	
	/**
	 * 图片的下载
	 * @param imgName 图片名
	 * @param response 响应对象
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public void download(String imgName, HttpServletResponse response) throws IOException;
	
	/**
	 * 保存
	 * @param organization 认证的企业信息和管理员审核后添加的信息
	 * @return 
	 */
	public void saveOrUpdateOrgInfo(Organization organization);

	/**
	 * 判断企业用户名是否存在
	 * @param mobileOrName
	 * @return 存在返回id值，否则返回 null
	 */
	public String  checkOrgName(String mobileOrName);

	/**
	 * 保存企业反馈信息
	 * @param advice
	 */
	public void saveAdviceInfo(OrganizationAdvice advice);

	/**
	 * 根据企业id查询认证的企业对象
	 * @param id 当前的企业id(手机号)
	 * @return 认证结果对象
	 */
	public Organization findAuthenOrgById(String id);

	/**
	 * 插入授权流水
	 * @param orgId 当前企业id
	 * @param cardNo 单据上用户身份证号
	 */
	public void InsertAuthorStream(String orgId, String cardNo);

	
	/**
	 * 根据条件查询 企业信息
	 * @param map 查询条件参数
	 * @param pageNo 当前页
	 * @param pageSize 每条显示数量
	 * @return PageResult 对象
	 */
	public PageResult<Organization> findByParam(Map<String,Object> map,Integer pageNo,Integer pageSize);

	

	
	/**
	 * 当企业扫码 点击查询时候，读取的单据历史，分为读取成功和读取失败
	 * 
	 * 
	 */
	
	
	

}
