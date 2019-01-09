package com.risepu.ftk.web.b.controller;

import com.risepu.ftk.server.domain.*;
import com.risepu.ftk.server.service.ChainService;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.server.service.PersonalUserService;
import com.risepu.ftk.server.service.ProofDocumentService;
import com.risepu.ftk.utils.ConfigUtil;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.BasicAction;
import com.risepu.ftk.web.Constant;
import com.risepu.ftk.web.SessionListener;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.*;
import com.risepu.ftk.web.exception.NotLoginException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ronghaohua
 */
@Controller
public class OrganizationController implements OrganizationApi {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${salt}")
    private String SALT;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ProofDocumentService proofDocumentService;

    @Autowired
    private ChainService chainService;

    @Autowired
    private PersonalUserService personalUserService;

    /**
     * 企业端注册
     *
     * @return
     */
    @Override
    public ResponseEntity<Response<String>> orgRegist(RegistRequest registVo, HttpSession session) {
        // 判断smsCode
        String code = (String) session.getAttribute(Constant.getSessionVerificationCodeSms());
        if (registVo.getSmsCode().equals(code)) {
            /** 判断企业是否已经注册 */
            String orgId = organizationService.checkOrgName(registVo.getMobile());
            if (orgId == null) {
                organizationService.orgReg(registVo.getMobile(), registVo.getPassword());
                RegisterUserReport report = new RegisterUserReport();
                report.setUserName(registVo.getMobile());
                report.setUserType(OrganizationUser.ORG_USER_TYPE);
                organizationService.saveRegisterReport(report);
                logger.debug("企业用户手机号--{},注册成功！", registVo.getMobile());
                return ResponseEntity.ok(Response.succeed("注册成功！"));
            } else {
                return ResponseEntity.ok(Response.failed(3, "该企业已经注册，请直接登录"));
            }
        } else {
            return ResponseEntity.ok(Response.failed(2, "验证码输入错误！"));
        }
    }

    @Override
    public void login() throws NotLoginException {
        throw new NotLoginException();
    }

    /**
     * 企业登录
     *
     * @param loginRequest 登录请求
     * @return 登录结果
     * @throws
     */

    @Override
    public ResponseEntity<Response<LoginResult>> orgLogin(OrgLoginRequest loginRequest, HttpServletRequest request)  {
        Subject subject = SecurityUtils.getSubject();
        String password = DigestUtils.md5Hex(loginRequest.getPassword() + SALT);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginRequest.getName(), password, true);
        usernamePasswordToken.setRememberMe(true);
        LoginResult loginResult = new LoginResult();
        try {
            subject.login(usernamePasswordToken);
            OrganizationUser orgUser = (OrganizationUser) subject.getPrincipal();
            String userId = orgUser.getId();

            /** 实现单一登录，剔除效果*/
            if (SessionListener.sessionMap.get(userId) != null) {
                BasicAction.forceLogoutUser(userId);
                SessionListener.sessionMap.put(userId, request.getSession());
            } else {
                SessionListener.sessionMap.put(userId, request.getSession());
            }
            /**  对象放入当前回话*/
            setCurrUserToSession((HttpSession) SessionListener.sessionMap.get(userId), orgUser);

            loginResult.setOrganizationUser(orgUser);
            loginResult.setMessage("登录成功！");
            Organization org = null;
            if (!StringUtils.isNumeric(loginRequest.getName())) {
                org = organizationService.findAuthenOrgByName(loginRequest.getName());
            }

            if (orgUser.getOrganizationId() != null) {
                org = organizationService.findAuthenOrgById(orgUser.getOrganizationId());
            }

            loginResult.setOrganization(org);
            logger.debug("企业用户--{},登录成功！", loginRequest.getName());
            return ResponseEntity.ok(Response.succeed(loginResult));
        } catch (UnknownAccountException e) {
            loginResult.setCode(4);
            loginResult.setMessage(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            loginResult.setCode(5);
            loginResult.setMessage("密码错误");
        } catch (AuthenticationException e) {
            loginResult.setCode(400);
            loginResult.setMessage("服务器忙，稍后重试");
        }

        return ResponseEntity.ok(Response.failed(loginResult.getCode(), loginResult.getMessage()));
    }

    @Override
    public ResponseEntity<Response<LoginResult>> loginSuccess(HttpSession session) {
        LoginResult loginResult = new LoginResult();
        Subject subject = SecurityUtils.getSubject();
        OrganizationUser user = (OrganizationUser) subject.getPrincipal();


        Organization org = null;
        if (user.getOrganizationId() != null) {
            org = organizationService.findAuthenOrgById(user.getOrganizationId());
        }
        loginResult.setOrganization(org);
        loginResult.setOrganizationUser(user);
        /** 如果有userId相同的回话 则剔除，保留当前回话*/
        if (SessionListener.sessionMap.get(user.getId()) != null) {
            BasicAction.forceLogoutUser(user.getId());
            SessionListener.sessionMap.put(user.getId(), session);
        } else {
            SessionListener.sessionMap.put(user.getId(), session);
        }

        return ResponseEntity.ok(Response.succeed(loginResult));
    }

    private void setCurrUserToSession(HttpSession session, OrganizationUser organizationUser) {
        session.setAttribute(Constant.getSessionCurrUser(), organizationUser);
    }

    /**
     * 忘记密码
     *
     * @param forgetRequest 表单数据
     * @param session
     * @return
     */
    @Override
    public ResponseEntity<Response<String>> orgForgetPwd(ForgetRequest forgetRequest, HttpSession session) {
        String salt = ConfigUtil.getValue("salt");
        /** 判断输入的企业信息是否存在  返回各自的id */
        String orgId = organizationService.checkOrgName(forgetRequest.getMobileOrName());
        if (orgId != null) {
            String smsCode = (String) session.getAttribute(Constant.getSessionVerificationCodeSms());

            if (forgetRequest.getSmsCode().equals(smsCode)) {

                String newPwd = DigestUtils.md5Hex(forgetRequest.getPassword() + salt);
                organizationService.changePwd(orgId, newPwd);
                logger.debug("企业用户   {}，修改密码成功！", forgetRequest.getMobileOrName());

                return ResponseEntity.ok(Response.succeed("密码修改成功"));
            } else {
                return ResponseEntity.ok(Response.failed(2, "验证码输入错误"));
            }

        } else {
            return ResponseEntity.ok(Response.failed(4, "该账号还未注册,请先注册"));
        }
    }

    /**
     * 修改密码
     */
    @Override
    public ResponseEntity<Response<String>> orgChangePwd(String password, String newpwd, HttpSession session) {
        OrganizationUser currUser = getCurrUser(session);
        if (currUser == null) {
            throw new NotLoginException("您的账号在另一设备登录，被迫下线");
        }
        OrganizationUser user = organizationService.findOrgUserById(currUser.getId());
        String salt = ConfigUtil.getValue("salt");
        password = DigestUtils.md5Hex(password + salt);
        newpwd = DigestUtils.md5Hex(newpwd + salt);

        if (user.getPassword().equals(password)) {
            organizationService.changePwd(user.getId(), newpwd);
            logger.debug("企业用户   {}，修改密码成功！", user.getId());
            return ResponseEntity.ok(Response.succeed("密码修改成功"));
        } else {
            return ResponseEntity.ok(Response.failed(7, "原始密码输入错误，请重新输入"));
        }
    }

    /**
     * 图片上传
     *
     * @param file 图片文件流
     * @return 保存的图片名
     */
    @Override
    public ResponseEntity<Response<String>> upload(MultipartFile file) {
        try {
            String fileName = organizationService.upload(file);
            return ResponseEntity.ok(Response.succeed(fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Response.failed(500, "上传失败"));
        }
    }

    /**
     * 图片下载
     *
     * @param ym       年 月
     * @param date     日期
     * @param imgName  图片名
     * @param response
     * @return
     */
    @Override
    public ResponseEntity<Response<String>> imgDownload(String ym, String date, String imgName, HttpServletResponse response) {
        try {
            String finalName = ym + "/" + date + "/" + imgName;
            organizationService.download(finalName, response);
            return ResponseEntity.ok(Response.succeed("图片下载成功"));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(Response.failed(9, "图片下载失败"));
        }
    }

    /**
     * 校验当前企业的审核状态
     *
     * @param request
     * @return Organization 企业的信息 (若为空则未审核)
     */
    @Override
    public ResponseEntity<Response<OrganizationStream>> checkAuthState(HttpServletRequest request) {
       HttpSession session = request.getSession();
        OrganizationUser currUser = getCurrUser(session);
        /** 若果当前回话的 user 为空，表示被挤掉*/
        if (currUser == null) {
            throw new NotLoginException("您的账号在另一设备登录，被迫下线");
        }
        /**  根据申请人手机号 查询审核状态*/
        OrganizationStream stream = organizationService.findAuthStreamByPhone(currUser.getId());
        return ResponseEntity.ok(Response.succeed(stream));
    }

    /**
     * 保存企业认证信息
     *
     * @param organizationStream 上传的的企业信息
     * @param session
     * @return 上传结果
     */
    @Override
    public ResponseEntity<Response<String>> orgAuthen(OrganizationStream organizationStream, HttpSession session) {
        OrganizationUser user = getCurrUser(session);
        if (user == null) {
            throw new NotLoginException("您的账号在另一设备登录，被迫下线");
        }

        /** 查找当前用户提交的组织机构代码证 是否已经通过审核*/
        Organization org = organizationService.findAuthenOrgById(organizationStream.getOrganization());
        if (org != null) {
            return ResponseEntity.ok(Response.failed(400, "该组织机构代码证书已经被注册，不得重复！"));
        }
        /** 该用户已经审核通过和一个企业绑定*/
        if (user.getOrganizationId() != null) {
            return ResponseEntity.ok(Response.failed(400, "该账号已经和企业绑定，不得重复申请！"));
        }

        /** 公司名称在审核中和审核成功的不可重复*/
        OrganizationStream stream = organizationService.findAuthStreamByNameAndState(organizationStream.getName(), OrganizationStream.CHECKING_STATE, OrganizationStream.CHECK_FAIL_STATE);

        if (stream != null && !stream.getId().equals(organizationStream.getId())) {
            return ResponseEntity.ok(Response.failed(400, "该公司名已经被注册，不得重复！"));
        }
        organizationStream.setState(OrganizationStream.CHECKING_STATE);
        organizationStream.setApplicationPhone(user.getId());
        organizationService.saveOrUpdateOrgStream(organizationStream);

        logger.debug("企业用户手机号--{},发送认证信息成功！", user.getId());
        return ResponseEntity.ok(Response.succeed("资料上传成功，等待审核"));
    }

    /**
     * 企业扫码单据 产生扫码流水(在跳转输入授权码之前)
     *
     * @param hash 单据hash
     *             用户身份证号
     * @return
     */
    @Override
    public ResponseEntity<Response<Long>> scanQR(String hash, HttpSession session) {
        /** 未审核通过的企业不允许扫描单据 */
        OrganizationUser currUser = getCurrUser(session);
        if (currUser == null) {
            throw new NotLoginException("您的账号在另一设备登录，被迫下线");
        }
        OrganizationUser user = organizationService.findOrgUserById(currUser.getId());
        Organization org = organizationService.findAuthenOrgById(user.getOrganizationId());
        String cardNo;
        try {
            cardNo = proofDocumentService.getDocumentPersonCardNo(hash);
        } catch (Exception e) {
            return ResponseEntity.ok(Response.failed(400, "失效的单据二维码"));
        }

        Long streamId = organizationService.InsertAuthorStream(org.getId(), cardNo);
        return ResponseEntity.ok(Response.succeed(streamId));
    }

    /**
     * 企业扫码验单历史查询
     *
     * @param pageRequest 分页请求参数
     * @param session
     * @return
     */
    @Override
    public ResponseEntity<Response<PageResult<VerifyHistory>>> verifyHistory(@RequestBody PageRequest pageRequest, HttpSession session) {
        OrganizationUser orgUser = getCurrUser(session);
        if (orgUser == null) {
            throw new NotLoginException("您的账号在另一设备登录，被迫下线");
        }
        OrganizationUser user = organizationService.findOrgUserById(orgUser.getId());
        PageResult<VerifyHistory> page = new PageResult();
        List content = new ArrayList();
        if (user.getOrganizationId() == null) {
            page.setContent(content);
            return ResponseEntity.ok(Response.succeed(page));
        }
        page = proofDocumentService.getVerfifyHistoryData(user.getOrganizationId(), pageRequest.getPageNo(), pageRequest.getPageSize(), pageRequest.getKey());
        return ResponseEntity.ok(Response.succeed(page));
    }

    /**
     * 企业开单历史单据查询
     *
     * @param pageRequest 分页请求参数
     * @param session
     * @return
     */
    @Override
    public ResponseEntity<Response<PageResult>> documentHistory(@RequestBody PageRequest pageRequest, HttpSession session) {
        /** 查询企业开单历史 */
        OrganizationUser currUser = getCurrUser(session);
        if (currUser == null) {
            throw new NotLoginException("您的账号在另一设备登录，被迫下线");
        }

        OrganizationUser user = organizationService.findOrgUserById(currUser.getId());
        PageResult document = new PageResult();

        if (user.getOrganizationId() == null) {
            document.setContent(new ArrayList());
            return ResponseEntity.ok(Response.succeed(document));
        }

        Organization org = organizationService.findAuthenOrgById(user.getOrganizationId());
        document = proofDocumentService.getDocuments(org.getId(), pageRequest.getPageNo(), pageRequest.getPageSize(), pageRequest.getKey());
        return ResponseEntity.ok(Response.succeed(document));
    }

    /**
     * 根据chainhash查询单据详情信息
     *
     * @param chainHash 证明单据的chainhash
     * @return 证明文档pdf路径
     */
    @Override
    public ResponseEntity<Response<ProofDocument>> documentInfo(String chainHash) {
        ProofDocument proofDocument = proofDocumentService.getDocument(chainHash);
        return ResponseEntity.ok(Response.succeed(proofDocument));
    }

    /**
     * 企业反馈信息录入
     *
     * @param advice 企业反馈信息
     * @return 保存结果
     */
    @Override
    public ResponseEntity<Response<String>> adviceInfo(OrganizationAdvice advice, HttpSession session) {
        OrganizationUser currUser = getCurrUser(session);
        if (currUser == null) {
            throw new NotLoginException("您的账号在另一设备登录，被迫下线");
        }
        advice.setOrgId(currUser.getId());
        organizationService.saveAdviceInfo(advice);
        return ResponseEntity.ok(Response.succeed("意见反馈成功！"));
    }

    private OrganizationUser getCurrUser(HttpSession session) {
        return (OrganizationUser) session.getAttribute(Constant.getSessionCurrUser());
    }

    /**
     * 退出登录
     */
    @Override
    public ResponseEntity<Response<String>> loginOut(HttpSession session) {
        session.setAttribute(Constant.getSessionCurrUser(), null);
        return ResponseEntity.ok(Response.succeed("退出登录成功"));
    }

    @Override
    public ResponseEntity<Response<String>> setDefaultTemplate(String templateId, boolean state, HttpSession session) {
        OrganizationUser currUser = getCurrUser(session);
        if (currUser == null) {
            throw new NotLoginException("您的账号在另一设备登录，被迫下线");
        }
        OrganizationUser user = organizationService.findOrgUserById(currUser.getId());
        Organization org = organizationService.findAuthenOrgById(user.getOrganizationId());
        if (state == false) {
            org.setDefaultTemId(null);
        } else {
            org.setDefaultTemId(Long.parseLong(templateId));
        }
        organizationService.updateOrg(org);
        return ResponseEntity.ok(Response.succeed("设置默认模板成功"));
    }

    @Override
    public ResponseEntity<Response<String>> qualifyQRCode(VerifyRequest verifyRequest) {
        AuthorizationStream authStream = personalUserService.findAuthorizationStreamById(verifyRequest.getStreamId());
        String authCode = authStream.getAuthCode();
        authStream.setChainHash(verifyRequest.getHash());

        if (verifyRequest.getAuthCode().equals(authCode)) {
            ProofDocument document = chainService.verify(verifyRequest.getHash(), verifyRequest.getCardNo());
            if (document != null) {
                /** 表示验证成功，添加验证历史*/
                authStream.setVerifyState(AuthorizationStream.VERIFY_STATE_PASS);
                personalUserService.update(authStream);
                String filePath = document.getFilePath();
                return ResponseEntity.ok(Response.succeed(filePath));

            } else {
                authStream.setVerifyState(AuthorizationStream.VERIFY_STATE_FAIL);
                personalUserService.update(authStream);
                return ResponseEntity.ok(Response.failed(400, "输入的身份证号和单据不匹配"));
            }

        } else {
            return ResponseEntity.ok(Response.failed(400, "授权码错误"));
        }
    }
}
