package indi.liudalei.eidea.base.web.listener;

        import indi.liudalei.eidea.base.service.SpringContextHolder;
        import indi.liudalei.eidea.base.service.UserSessionService;
        import indi.liudalei.eidea.base.web.content.UserOnlineContent;

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
