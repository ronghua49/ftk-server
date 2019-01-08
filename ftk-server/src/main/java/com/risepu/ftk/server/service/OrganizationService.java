package com.risepu.ftk.server.service;

import com.risepu.ftk.server.domain.*;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.b.dto.LoginResult;
import net.lc4ever.framework.remote.annotation.Remote;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;


/**
 * @author ronghaohua
 */
@Remote(path = "/org")
public interface OrganizationService {

    /**
     * @param phone    手机号
     * @param password 密码
     * @return
     */
    String orgReg(String phone, String password);

    /**
     * @param phoneOrName
     * @param password
     * @return LoginResult 登录返回结果
     */
    LoginResult orgLogin(String phoneOrName, String password);

    /**
     * @param id     手机号 获得 组织机构代码号
     * @param newPwd 新密码
     */
    void changePwd(String id, String newPwd);

    /**
     * 上传图片
     *
     * @param file
     * @return 图片的存储名
     * @throws IOException
     * @throws IllegalStateException
     */
    String upload(MultipartFile file) throws IllegalStateException, IOException;

    /**
     * 图片的下载
     *
     * @param imgName  图片名
     * @param response 响应对象
     * @throws FileNotFoundException
     * @throws IOException
     */
    void download(String imgName, HttpServletResponse response) throws IOException;

    /**
     * 保存
     *
     * @param organization 认证的企业信息和管理员审核后添加的信息
     * @return
     */
    void save(Organization organization);

    /**
     * 判断企业用户名是否存在
     *
     * @param mobileOrName
     * @return 存在返回id值，否则返回 null
     */
    String checkOrgName(String mobileOrName);

    /**
     * 保存企业反馈信息
     *
     * @param advice
     */
    void saveAdviceInfo(OrganizationAdvice advice);

    /**
     * 根据企业id查询认证的企业对象
     *
     * @param id 当前的企业id(手机号)
     * @return 认证结果对象
     */
    Organization findAuthenOrgById(String id);

    /**
     * 插入授权流水
     *
     * @param orgId  当前企业id
     * @param cardNo 单据上用户身份证号
     */
    Long InsertAuthorStream(String orgId, String cardNo);

    /**
     * 根据条件查询 企业信息
     *
     * @param map      查询条件参数
     * @param pageNo   当前页
     * @param pageSize 每条显示数量
     * @return PageResult 对象
     */
    PageResult<OrganizationStream> findByParam(Map<String, Object> map, Integer pageNo, Integer pageSize) throws UnsupportedEncodingException;

    /**
     * 修改企业用户信息
     *
     * @param user
     */
    void updateOrgUser(OrganizationUser user);

    /**
     * 根据id查询OrganizationUser
     *
     * @param id
     * @return
     */
    OrganizationUser findOrgUserById(String id);

    /**
     * 更新企业信息
     *
     * @param currOrg
     */
    void updateOrg(Organization currOrg);

    /**
     * 根据企业id查询验证成功的扫描单据流水
     *
     * @param organizationId
     * @return
     */
    List<AuthorizationStream> querySucceedAuthStreamByOrgId(String organizationId);

    /**
     * 根据企业id查询用户
     *
     * @param id
     * @return
     */
    OrganizationUser findOrgUserByOrgId(String id);

    /**
     * 增加企业认证流水
     *
     * @param organizationStream
     */
    void saveOrgStream(OrganizationStream organizationStream);

    /**
     * 根据申请人手机号查询授权流水
     *
     * @param id
     * @return
     */
    OrganizationStream findAuthStreamByPhone(String id);

    /**
     * 更新用户发起的认证流水
     *
     * @param organizationStream
     */
    void updateOrgStream(OrganizationStream organizationStream);

    /**
     * 根据id查询企业认证流水
     *
     * @param streamId
     * @return
     */
    OrganizationStream findAuthStreamById(Long streamId);

    /**
     * 根据组织机构代码证和状态查询企业认证流水
     *
     * @param organization
     * @return
     */
    List<OrganizationStream> findAuthStreamByOrgnization(String organization, Integer state);

    /**
     * 根据企业名查询已经认证的企业
     *
     * @param name
     * @return
     */
    Organization findAuthenOrgByName(String name);

    /**
     *新增或者修改企业发起认证的流水
     * @param organizationStream
     */
    void saveOrUpdateOrgStream(OrganizationStream organizationStream);


    /**
     * 根据参数查询企业注册信息
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageResult<OrganizationStream> findOrgRegStreamByMap(Map<String,Object> map, Integer pageNo, Integer pageSize) throws UnsupportedEncodingException;

    /**
     * 根据参数查询企业和个人注册用户
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageResult<RegisterUserReport> findRegUserByMap(Map<String,Object> map, Integer pageNo, Integer pageSize);


    /**
     * 保存企业注册信息到报表
     * @param report
     */
    void saveRegisterReport(RegisterUserReport report);

    /**
     * 根据状态和企业名查询 企业流水
     * @param name
     * @param checkingState
     * @param checkFailState
     * @return
     */
    OrganizationStream findAuthStreamByNameAndState(String name, Integer checkingState, Integer checkFailState);
}
