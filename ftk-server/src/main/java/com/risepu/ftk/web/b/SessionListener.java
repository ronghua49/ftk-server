package com.risepu.ftk.web.b;    /*
 * @author  Administrator
 * @date 2018/12/28
 */

import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.web.Constant;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;


/**
 * @author ronghaohua
 */
public class SessionListener implements HttpSessionListener {

    public static HashMap sessionMap = new HashMap();
    @Override
    public void sessionCreated(HttpSessionEvent hse) {
        HttpSession session = hse.getSession();
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
        HttpSession session = hse.getSession();
        DelSession(session);
    }
    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
            // 删除单一登录中记录的变量
            if(session.getAttribute(Constant.getSessionCurrUser())!=null){
                OrganizationUser  user = (OrganizationUser) session.getAttribute(Constant.getSessionCurrUser());
                SessionListener.sessionMap.remove(user.getId());
            }
        }
    }



}
