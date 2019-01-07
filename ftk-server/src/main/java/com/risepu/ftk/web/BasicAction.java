package com.risepu.ftk.web;    /*
 * @author  ronghaohua
 * @date 2018/12/28
 */

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @author ronghaohua
 */
public class BasicAction {

    public static void forceLogoutUser(String userId){

        HttpSession hs = (HttpSession) SessionListener.sessionMap.get(userId);
        SessionListener.sessionMap.remove(userId);
        Enumeration e = hs.getAttributeNames();
        while (e.hasMoreElements()) {
            String sessionName = (String) e.nextElement();
            // 清空session
            hs.removeAttribute(sessionName);
        }

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //hs.setAttribute(Constant.getSessionCurrUser(),null);
    }



}
