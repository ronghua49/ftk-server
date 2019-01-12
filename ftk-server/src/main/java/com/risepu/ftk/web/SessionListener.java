package com.risepu.ftk.web;    /*
 * @author  Administrator
 * @date 2018/12/28
 */

import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.web.exception.KickoutException;
import com.risepu.ftk.web.exception.NotLoginException;

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

    public static void judgeLogin(OrganizationUser organizationUser){
        if (organizationUser == null) {
            throw new NotLoginException();
        }

    }

    public static void judgeKickOut(String userId,HttpSession session){

        String[] sessionIds = SessionListener.sessionMap.get(userId);
        if(sessionIds!=null&&session.getId().equals(sessionIds[1])){
            throw new KickoutException();
        }

    }

}
