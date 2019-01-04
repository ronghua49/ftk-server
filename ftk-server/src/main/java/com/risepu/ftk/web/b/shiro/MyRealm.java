package com.risepu.ftk.web.b.shiro;    /*
 * @author  Administrator
 * @date 2019/1/2
 */

import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.server.service.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
        String username = usernamePasswordToken.getUsername();

        if (StringUtils.isNumeric(username)) {
            OrganizationUser orgUser = organizationService.findOrgUserById(username);
            if (orgUser == null) {
                throw new UnknownAccountException("此手机号还未注册，请注册！");
            } else {
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
                logger.info("{}--登录系统", org.getName());
                //返回简单的 认证信息对象， 当前对象，认证证书，当前类的详情对象名(和传过来的token进行对比)
                return new SimpleAuthenticationInfo(orgUser, orgUser.getPassword(), getName());
            }


        }
    }
}
