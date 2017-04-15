package com.dsdl.eidea.base.web.listener;

        import com.dsdl.eidea.base.service.SpringContextHolder;
        import com.dsdl.eidea.base.service.UserSessionService;
        import com.dsdl.eidea.base.web.content.UserOnlineContent;
        import org.apache.shiro.SecurityUtils;
        import org.apache.shiro.subject.Subject;

        import javax.servlet.http.HttpSession;
        import javax.servlet.http.HttpSessionEvent;
        import javax.servlet.http.HttpSessionListener;

/**
 * Created by 刘大磊 on 2016/12/30 15:49.
 */
public class OnLineUserListener implements HttpSessionListener {
    UserSessionService userSessionService = null;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        if (userSessionService == null) {
            userSessionService = SpringContextHolder.getBean(UserSessionService.class);
        }
        HttpSession session = httpSessionEvent.getSession();
        UserOnlineContent.removeUserBySessionId(session.getId());
        userSessionService.saveLoginOutDate(session.getId());

    }
}
