package com.risepu.ftk.web.b.shiro;    /*
 * @author  Administrator
 * @date 2019/1/5
 */

import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.web.Constant;
import com.risepu.ftk.web.exception.NotLoginException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MyAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                return this.executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            throw new NotLoginException();
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpSession session = ((HttpServletRequest) request).getSession();
        OrganizationUser orgUser = (OrganizationUser) token.getPrincipal();
        session.setAttribute(Constant.getSessionCurrUser(), orgUser);
        return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        throw new NotLoginException();
    }
}
