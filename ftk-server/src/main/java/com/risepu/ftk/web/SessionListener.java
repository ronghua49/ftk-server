package com.risepu.ftk.web;    /*
 * @author  Administrator
 * @date 2018/12/28
 */

import com.risepu.ftk.server.domain.OrganizationUser;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;


/**
 * @author ronghaohua
 */
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    public static Map<String,String[]>  sessionMap= new HashMap();

    @Override
    public void sessionCreated(HttpSessionEvent hse) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
        HttpSession session = hse.getSession();
        DelSession(session);
    }

    public static synchronized void DelSession(HttpSession session) {
             // 删除单一登录中记录的变量
        OrganizationUser user = (OrganizationUser) session.getAttribute(Constant.getSessionCurrUser());
        if (user==null) {
            return;
        }
        String sessionId = session.getId();
        String[] sessionIds = sessionMap.get(user.getId());
        if (sessionIds==null) {
            return;
        }


        if (sessionId.equals(sessionIds[0])) {
            // 当前为登录状态的会话
            sessionMap.remove(user.getId());
        } else if (sessionId.equals(sessionIds[1])) {
            // 被踢出的会话
            sessionIds[1] = null;
        }

    }
}
