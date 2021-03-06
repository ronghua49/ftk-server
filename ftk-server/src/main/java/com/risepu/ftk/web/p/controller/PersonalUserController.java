
package com.risepu.ftk.web.p.controller;

import com.risepu.ftk.server.domain.AuthorizationStream;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.PersonalUser;
import com.risepu.ftk.server.domain.ProofDocument;
import com.risepu.ftk.server.service.ChainService;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.server.service.PersonalUserService;
import com.risepu.ftk.server.service.SmsService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.Constant;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.b.dto.PageRequest;
import com.risepu.ftk.web.exception.NotLoginException;
import com.risepu.ftk.web.p.dto.AuthHistoryInfo;
import com.risepu.ftk.web.p.dto.LoginRequest;
import com.risepu.ftk.web.p.dto.LoginResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
public class PersonalUserController implements PersonzalUserApi {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PersonalUserService personalService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private ChainService chainService;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public void personalScanDoc(String hash, HttpSession session, HttpServletResponse response) throws IOException {
        session.setAttribute(Constant.getSessionChainHash(), hash);
        response.sendRedirect("/ftk/p");
    }

    /**
     * 用户登录　通过扫描二维码跳转登录页面
     *
     * @param loginRequest
     * @param request
     * @return 需要
     */
    @Override
    public ResponseEntity<Response<LoginResult>> personalLogin(LoginRequest loginRequest, HttpServletRequest request) {
        String smsCode = getSmsCode(request);
        boolean identify = smsService.identify(loginRequest.getInCode(), smsCode);
        LoginResult loginResult = new LoginResult();
        if (identify) {
          String no = loginRequest.getCardNo();
            /** 解析单据信息 */
            String chainHash = (String) request.getSession().getAttribute(Constant.getSessionChainHash());
            /** 校验用户输入的身份证号是否和单据信息一致 */
            ProofDocument document = chainService.verify(chainHash, no);
            if (document != null) {
                PersonalUser personalUser = personalService.findUserByNo(no,loginRequest.getPhone());
                if (personalUser != null) {
                    loginResult.setMessage("登录成功");
                    loginResult.setPersonalUser(personalUser);
                } else {
                    PersonalUser user = new PersonalUser();
                    PersonalUser.ID id=new PersonalUser.ID();
                    id.setId(no);
                    id.setMobile(loginRequest.getPhone());
                    user.setId(id);
                    personalService.savePersonUser(user);
                    personalUser = user;
                }
                loginResult.setPersonalUser(personalUser);
                request.getSession().setAttribute(Constant.getSessionCurrUser(), personalUser);

                /** 根据身份证 查询新的请求授权的企业名和 当前授权流水的id*/

               Map<Long, String> requestAuthMap = personalService.findNewRequestByCardNo(no);
                if (requestAuthMap != null) {
                    loginResult.setMap(requestAuthMap);
                }
                logger.debug("用户手机号--{}，登录成功", personalUser.getId().getMobile());
                return ResponseEntity.ok(Response.succeed(loginResult));
            } else {
                loginResult.setMessage("输入的身份证号和单据不匹配");
                return ResponseEntity.ok(Response.failed(10, loginResult.getMessage()));
            }
        } else {
            loginResult.setMessage("验证码输入错误");
            return ResponseEntity.ok(Response.failed(2, loginResult.getMessage()));
        }
    }

    @Override
    public ResponseEntity<Response< Map<Long, String>>> getNewAuthingStream(HttpServletRequest request) {
        PersonalUser currUser = getCurrUser(request);
        if(currUser==null){
            throw  new NotLoginException();
        }
        Map<Long, String> map = personalService.findNewRequestByCardNo(currUser.getId().getId());
        return  ResponseEntity.ok(Response.succeed(map));
    }
    private String getSmsCode(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(Constant.getSessionVerificationCodeSms());
    }


    /**
     * 用户发起授权或拒绝
     *
     * @param streamId 当前扫描流水id
     * @param state    授权状态
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<Response<String>> personAuth(String streamId, String state, HttpServletRequest request) {

        String message = "";
        PersonalUser personalUser = getCurrUser(request);
        if (personalUser == null) {
            return ResponseEntity.ok(Response.failed(400, "请重新扫码登录"));
        }
        AuthorizationStream stream = personalService.findAuthorizationStreamById(Long.parseLong(streamId));

        if (stream == null) {
            return ResponseEntity.ok(Response.failed(11, "错误的流水id"));
        }

        Organization org = organizationService.findAuthenOrgById(stream.getOrgId());

        if (!stream.getAuthState().equals(AuthorizationStream.AUTH_STATE_NEW)) {
            return ResponseEntity.ok(Response.failed(400, "该企业已经被授权（拒绝），请不要重复操作"));
        }

        /** 判断授权 */
        if (Integer.parseInt(state) == (AuthorizationStream.AUTH_STATE_PASS)) {
            /** 发送验证码 */
            Map<String, String> params = new HashMap<>();
            params.put("company", org.getName());
            String code = smsService.sendCode(personalUser.getId().getMobile(), SmsService.authTemplateCode, params);
            stream.setAuthCode(code);
            stream.setAuthState(AuthorizationStream.AUTH_STATE_PASS);
            message = "授权码下发成功";

        } else {
            stream.setAuthState(AuthorizationStream.AUTH_STATE_REFUSE);
            message = "授权已拒绝";
        }

        personalService.update(stream);

        return ResponseEntity.ok(Response.succeed(message));
    }


    /**
     * 查询历史授权记录
     *
     * @param pageRequest 分页参数
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<Response<PageResult<AuthHistoryInfo>>> getAuthInfoList(PageRequest pageRequest, HttpServletRequest request) {
        PersonalUser user = getCurrUser(request);
        if (user == null) {
            return ResponseEntity.ok(Response.failed(400, "请重新扫码登录"));
        }
        PageResult<AuthHistoryInfo> pageResult = personalService.queryHistoryByParam(pageRequest.getKey(), pageRequest.getPageNo(), pageRequest.getPageSize(), user.getId().getId());
        return ResponseEntity.ok(Response.succeed(pageResult));
    }

    private PersonalUser getCurrUser(HttpServletRequest request) {
        return (PersonalUser) request.getSession().getAttribute(Constant.getSessionCurrUser());
    }

}
