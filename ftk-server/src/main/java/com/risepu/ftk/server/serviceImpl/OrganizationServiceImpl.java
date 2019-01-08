package com.risepu.ftk.server.serviceImpl;

import com.risepu.ftk.server.domain.*;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.utils.ConfigUtil;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.b.dto.LoginResult;
import net.lc4ever.framework.format.DateFormatter;
import net.lc4ever.framework.service.GenericCrudService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ronghaohua
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {


    @Value("${salt}")
    private String SALT;

    @Autowired
    private GenericCrudService crudService;

    public void setCrudService(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    @Override
    public String orgReg(String phone, String password) {
        OrganizationUser org = new OrganizationUser();
        org.setId(phone);
        password = DigestUtils.md5Hex(password + SALT);
        org.setPassword(password);
        crudService.save(org);
        return "success";
    }

    @Override
    public LoginResult orgLogin(String phoneOrName, String password) {
        String secutityPwd = DigestUtils.md5Hex(password + SALT);
        LoginResult loginResult = new LoginResult();

        if (StringUtils.isNumeric(phoneOrName)) {
            /** 使用手机号登录 */

            OrganizationUser org = crudService.uniqueResultByProperty(OrganizationUser.class, "id", phoneOrName);

            if (org == null) {
                loginResult.setCode(4);
                loginResult.setMessage("此手机号还未注册，请注册！");
            }

            if (org != null && org.getPassword().equals(secutityPwd)) {
                loginResult.setCode(0);
                loginResult.setMessage("登录成功！");
                loginResult.setOrganizationUser(org);


                /** 判断是否为审核通过的企业用户 */
                Organization organization = null;
                if (org.getOrganizationId() != null) {
                    organization = crudService.uniqueResultByProperty(Organization.class, "id", org.getOrganizationId());
                }

                loginResult.setOrganization(organization);

            } else {
                loginResult.setCode(5);
                loginResult.setMessage("密码错误！");
            }


        } else {
            /** 使用企业名登录 */
            Organization org = crudService.uniqueResultByProperty(Organization.class, "name", phoneOrName);

            if (org != null) {
                OrganizationUser orgUser = crudService.uniqueResultByProperty(OrganizationUser.class, "organizationId",
                        org.getId());
                if (orgUser.getPassword().equals(secutityPwd)) {
                    loginResult.setCode(0);
                    loginResult.setMessage("登录成功！");
                    loginResult.setOrganization(org);
                    loginResult.setOrganizationUser(orgUser);
                } else {
                    loginResult.setCode(5);
                    loginResult.setMessage("密码错误！");
                }
            } else {
                loginResult.setCode(6);
                loginResult.setMessage("企业不存在，或未认证！");
            }
        }
        return loginResult;
    }

    @Override
    public void changePwd(String id, String newPwd) {
        OrganizationUser orgUser = crudService.get(OrganizationUser.class, id);
        orgUser.setPassword(newPwd);
        crudService.update(orgUser);
    }

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public String upload(MultipartFile file) throws IllegalStateException, IOException {
        String name = UUID.randomUUID().toString().replaceAll("-", "");
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        name = new SimpleDateFormat("yyyy-MM/dd/").format(new Date()) + name + "." + ext;
        /** 上传图片到指定地址路径 */
        File dir = new File(uploadPath);
        File target = new File(dir, name);
        target.getParentFile().mkdirs();

        file.transferTo(target);
        return name;
    }

    @Override
    public void download(String imgName, HttpServletResponse response) throws IOException {
        File dir = new File(ConfigUtil.getValue("file.upload.path"));
        File target = new File(dir, imgName);

        InputStream in = new FileInputStream(target);
        OutputStream out = response.getOutputStream();
        IOUtils.copy(in, out);
        out.flush();
        out.close();
        in.close();

    }

    @Override
    public void save(Organization organization) {
        crudService.save(organization);
    }

    @Override
    public String checkOrgName(String mobileOrName) {

        if (StringUtils.isNumeric(mobileOrName)) {
            OrganizationUser user = crudService.uniqueResultByProperty(OrganizationUser.class, "id", mobileOrName);
            if (user != null) {
                return user.getId();
            }
        } else {
            Organization user = crudService.uniqueResultByProperty(Organization.class, "name", mobileOrName);
            if (user != null) {
                return user.getId();
            }
        }

        return null;
    }

    @Override
    public void saveAdviceInfo(OrganizationAdvice advice) {
        crudService.save(advice);
    }

    @Override
    public Organization findAuthenOrgById(String id) {

        return crudService.get(Organization.class, id);
    }

    @Override
    public Long InsertAuthorStream(String orgId, String cardNo) {

        AuthorizationStream stream = new AuthorizationStream();

        stream.setOrgId(orgId);
        stream.setPersonId(cardNo);
        stream.setAuthState(AuthorizationStream.AUTH_STATE_NEW);
        Long streamId = crudService.save(stream);
        return streamId;

    }

    @Override
    public PageResult<OrganizationStream> findByParam(Map<String, Object> map, Integer pageNo, Integer pageSize) throws UnsupportedEncodingException {
        Integer firstIndex = (pageNo) * pageSize;

        String hql = "";
        String hql2 = "select count(*) ";
        int total = 0;
        List<OrganizationStream> orgs = new ArrayList<OrganizationStream>();

        String key = (String) map.get("key");

        String startTime = (String) map.get("startTime");
        String endTime = (String) map.get("endTime");
        Integer state = (Integer) map.get("state");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate;
        Date nextDate = null;
        if (StringUtils.isNotEmpty(startTime)) {
            try {
                startDate = format.parse(startTime);
                endDate = format.parse(endTime);
                nextDate = DateFormatter.startOfDay(DateFormatter.nextDay(endDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        if (StringUtils.isNotEmpty(key) && state == null && startDate == null) {
            key = new String(key.getBytes("ISO8859-1"), "utf-8");
            hql = "from OrganizationStream where name like ?1 order by createTimestamp desc";
            total = crudService.uniqueResultHql(Long.class, hql2 + hql, "%" + key + "%").intValue();
            orgs = crudService.hql(OrganizationStream.class, firstIndex, pageSize, hql, "%" + key + "%");

        } else if (StringUtils.isNotEmpty(key) && state != null && startDate == null) {
            key = new String(key.getBytes("ISO8859-1"), "utf-8");
            hql = "from OrganizationStream where name like ?1 and state = ?2 order by createTimestamp desc";
            total = crudService.uniqueResultHql(Long.class, hql2 + hql, "%" + key + "%", state).intValue();
            orgs = crudService.hql(OrganizationStream.class, firstIndex, pageSize, hql, "%" + key + "%", state);

        } else if (StringUtils.isNotEmpty(key) && state != null && startDate != null) {
            key = new String(key.getBytes("ISO8859-1"), "utf-8");
            hql = "from OrganizationStream where name like ?1 and state=?2 and createTimestamp between ?3 and ?4 order by createTimestamp desc";
            total = crudService.uniqueResultHql(Long.class, hql2 + hql, "%" + key + "%", state, startDate, nextDate).intValue();
            orgs = crudService.hql(OrganizationStream.class, firstIndex, pageSize, hql, "%" + key + "%", state, startDate, nextDate);

        } else if (StringUtils.isEmpty(key) && state == null && startDate != null) {

            hql = "from OrganizationStream where createTimestamp between ?1 and ?2 order by createTimestamp desc";
            total = crudService.uniqueResultHql(Long.class, hql2 + hql, startDate, nextDate).intValue();
            orgs = crudService.hql(OrganizationStream.class, firstIndex, pageSize, hql, startDate, nextDate);

        } else if (StringUtils.isEmpty(key) && state != null && startDate != null) {

            hql = "from OrganizationStream where state=?1 and createTimestamp between ?2 and ?3 order by createTimestamp desc";
            total = crudService.uniqueResultHql(Long.class, hql2 + hql, state, startDate, nextDate).intValue();
            orgs = crudService.hql(OrganizationStream.class, firstIndex, pageSize, hql, state, startDate, nextDate);

        } else if (StringUtils.isEmpty(key) && state != null && startDate == null) {
            hql = "from OrganizationStream where state=?1 order by createTimestamp desc";
            total = crudService.uniqueResultHql(Long.class, hql2 + hql, state).intValue();
            orgs = crudService.hql(OrganizationStream.class, firstIndex, pageSize, hql, state);
        } else {
            hql = "from OrganizationStream order by createTimestamp desc";
            total = crudService.uniqueResultHql(Long.class, hql2 + hql).intValue();
            orgs = crudService.hql(OrganizationStream.class, firstIndex, pageSize, hql);
        }
        PageResult<OrganizationStream> pageResult = new PageResult<>();
        pageResult.setResultCode("SUCCESS");
        pageResult.setNumber(pageNo);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages(total, pageSize);
        pageResult.setTotalElements(total);
        pageResult.setContent(orgs);
        return pageResult;
    }

    /**
     * 修改企业用户信息
     *
     * @param user
     */
    @Override
    public void updateOrgUser(OrganizationUser user) {
        crudService.update(user);
    }

    /**
     * 根据id查询OrganizationUser
     *
     * @param id
     * @return
     */
    @Override
    public OrganizationUser findOrgUserById(String id) {
        return crudService.get(OrganizationUser.class, id);
    }

    /**
     * 更新企业信息
     *
     * @param currOrg
     */
    @Override
    public void updateOrg(Organization currOrg) {
        crudService.update(currOrg);
    }

    /**
     * 根据企业id查询验证成功的扫描单据流水
     *
     * @param organizationId
     * @return
     */
    @Override
    public List<AuthorizationStream> querySucceedAuthStreamByOrgId(String organizationId) {
        return crudService.hql(AuthorizationStream.class, "from AuthorizationStream where orgId =?1 and verifyState= ?2", organizationId, AuthorizationStream.VERIFY_STATE_PASS);
    }

    /**
     * 根据企业id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public OrganizationUser findOrgUserByOrgId(String id) {
        return crudService.uniqueResultHql(OrganizationUser.class, "from OrganizationUser where organizationId =?1", id);
    }

    /**
     * 增加企业认证流水
     *
     * @param organizationStream
     */
    @Override
    public void saveOrgStream(OrganizationStream organizationStream) {
        crudService.save(organizationStream);
    }

    /**
     * 根据申请人手机号查询授权流水
     *
     * @param id
     * @return
     */
    @Override
    public OrganizationStream findAuthStreamByPhone(String id) {
        List<OrganizationStream> streamList = crudService.hql(OrganizationStream.class, "from OrganizationStream where applicationPhone =?1 order by createTimestamp desc ", id);
        if (streamList != null && !streamList.isEmpty()) {
            return streamList.get(0);
        }
        return null;
    }

    /**
     * 更新用户发起的认证流水
     *
     * @param organizationStream
     */
    @Override
    public void updateOrgStream(OrganizationStream organizationStream) {
        crudService.update(organizationStream);
    }

    /**
     * 根据id查询企业认证流水
     *
     * @param streamId
     * @return
     */
    @Override
    public OrganizationStream findAuthStreamById(Long streamId) {
        return crudService.get(OrganizationStream.class, streamId);
    }

    /**
     * 根据组织机构代码证查询企业认证流水
     *
     * @param organization
     * @return
     */
    @Override
    public List<OrganizationStream> findAuthStreamByOrgnization(String organization, Integer state) {
        return crudService.hql(OrganizationStream.class, "from OrganizationStream where organization =?1 and state =?2", organization, state);
    }

    /**
     * 根据企业名查询已经认证的企业
     *
     * @param name
     * @return
     */
    @Override
    public Organization findAuthenOrgByName(String name) {
        return crudService.uniqueResultHql(Organization.class, "from Organization where name =?1", name);
    }

    /**
     * 新增或者修改企业发起认证的流水
     *
     * @param organizationStream
     */
    @Override
    public void saveOrUpdateOrgStream(OrganizationStream organizationStream) {
        crudService.saveOrUpdate(organizationStream);
    }

    /**
     * 根据参数查询企业注册信息
     *
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<OrganizationStream> findOrgRegStreamByMap(Map<String, Object> map, Integer pageNo, Integer pageSize) throws UnsupportedEncodingException {
        Integer firstIndex = (pageNo) * pageSize;
        String hql = "from OrganizationStream where 1=1 ";
        String hql1 = " order by createTimestamp desc";
        String hql2 = "select count(*) ";
        String orgName = (String) map.get("orgName");
        String legalPerson = (String) map.get("legalPerson");
        String industry = (String) map.get("industry");
        String startTime = (String) map.get("startTime");
        String endTime = (String) map.get("endTime");
        Integer state = (Integer) map.get("state");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nextDate = null;
        if (StringUtils.isNotEmpty(startTime)) {
            try {
                Date next = DateFormatter.startOfDay(DateFormatter.nextDay(format.parse(endTime)));
                nextDate = format.format(next);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        if (StringUtils.isNotEmpty(orgName)) {
            orgName = new String(orgName.getBytes("ISO8859-1"), "utf-8");

            hql += "and name like '%" + orgName + "%'";
        }

        if (StringUtils.isNotEmpty(legalPerson)) {
            legalPerson = new String(legalPerson.getBytes("ISO8859-1"), "utf-8");
            hql += " and legalPerson like '%" + legalPerson + "%'";
        }
        if (StringUtils.isNotEmpty(industry)) {
            hql += " and code in (select code from DictionaryData where dictId =(select id from Dictionary where dictCode = '" + industry + "'))";
        }
        if (StringUtils.isNotEmpty(startTime)) {
            hql += " and createTimestamp  between '" + startTime + "' and '" + nextDate + "'";
        }

        if (state != null) {
            hql += " and state= " + state;
        }
        List<OrganizationStream> organizationStreams = crudService.hql(OrganizationStream.class, firstIndex, pageSize, hql + hql1);
        int total = crudService.uniqueResultHql(Long.class, hql2 + hql).intValue();
        PageResult<OrganizationStream> pageResult = new PageResult<>();
        pageResult.setResultCode("SUCCESS");
        pageResult.setNumber(pageNo);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages(total, pageSize);
        pageResult.setTotalElements(total);
        pageResult.setContent(organizationStreams);
        return pageResult;
    }

    /**
     * 根据参数查询 注册用户
     *
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<RegisterUserReport> findRegUserByMap(Map<String, Object> map, Integer pageNo, Integer pageSize) {

        Integer firstIndex = (pageNo) * pageSize;

        String userType = (String) map.get("userType");
        String startTime = (String) map.get("startTime");
        String endTime = (String) map.get("endTime");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nextDate = null;
        if (StringUtils.isNotEmpty(startTime)) {
            try {
                Date next = DateFormatter.startOfDay(DateFormatter.nextDay(format.parse(endTime)));
                nextDate = format.format(next);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String hql = "from RegisterUserReport where 1=1 ";
        String prefixSql = "select count(*) ";

        if (StringUtils.isNotEmpty(userType)) {
            hql += "and userType = " + Integer.parseInt(userType);
        }
        if (StringUtils.isNotEmpty(startTime)) {
            hql += "and createTimestamp between '" + startTime + "' and '" + nextDate + "'";
        }

        hql += " order by createTimestamp desc";

        List<RegisterUserReport> reportList = crudService.hql(RegisterUserReport.class, firstIndex, pageSize, hql);
        int total = crudService.uniqueResultHql(Long.class, prefixSql + hql).intValue();

        PageResult<RegisterUserReport> pageResult = new PageResult<>();
        pageResult.setResultCode("SUCCESS");
        pageResult.setNumber(pageNo);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages(total, pageSize);
        pageResult.setTotalElements(total);
        pageResult.setContent(reportList);
        return pageResult;
    }

    /**
     * 保存企业注册信息到报表
     *
     * @param report
     */
    @Override
    public void saveRegisterReport(RegisterUserReport report) {
        crudService.save(report);
    }

    /**
     * 根据状态和企业名查询 企业流水
     *
     * @param name
     * @param checkingState
     * @param checkFailState
     * @return
     */
    @Override
    public OrganizationStream findAuthStreamByNameAndState(String name, Integer checkingState, Integer checkFailState) {

        return crudService.uniqueResultHql(OrganizationStream.class, "from OrganizationStream where name =?1 and state in (2,3)", name);
    }

}
