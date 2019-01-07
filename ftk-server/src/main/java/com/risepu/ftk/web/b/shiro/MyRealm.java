package com.risepu.ftk.web.b.shiro;    /*
 * @author  Administrator
 * @date 2019/1/2
 */

import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.server.service.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public class MyRealm extends AuthorizingRealm {

    Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private OrganizationService organizationService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String username = usernamePasswordToken.getUsername();
        if (StringUtils.isNumeric(username)) {
            OrganizationUser orgUser = organizationService.findOrgUserById(username);
            if (orgUser == null) {
                throw new UnknownAccountException("此手机号还未注册，请注册！");
            } else {
               // kickOutSession(orgUser.getId(),session);
                logger.info("{}--登录系统", orgUser.getId());
                //返回简单的 认证信息对象， 当前对象，认证证书，当前类的详情对象名(和传过来的token进行对比)
                return new SimpleAuthenticationInfo(orgUser, orgUser.getPassword(), getName());
            }

        } else {
            Organization org = organizationService.findAuthenOrgByName(username);
            if (org == null) {
                throw new UnknownAccountException("企业不存在，或未认证！");
            } else {
                OrganizationUser orgUser = organizationService.findOrgUserByOrgId(org.getId());

               // kickOutSession(orgUser.getId(),session);
                logger.info("{}--登录系统", org.getName());
                //返回简单的 认证信息对象， 当前对象，认证证书，当前类的详情对象名(和传过来的token进行对比)
                return new SimpleAuthenticationInfo(orgUser, orgUser.getPassword(), getName());
            }

        }
    }

    private void kickOutSession(String userId,Session session1) {


        //踢出同一账号的其他session
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager)securityManager.getSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
        for(Session session:sessions){
            SimplePrincipalCollection collection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(collection!=null){
                OrganizationUser user = (OrganizationUser) collection.getPrimaryPrincipal();

              //所有的回话session对应的user,如果和当前回话userId相同，则删除回话sesion
                if(user!=null&&userId.equals(user.getId())) {
                    sessionManager.getSessionDAO().delete(session);

                }
            }
        }

      //  sessionManager.getSessionDAO().create(session1);
    }
}
