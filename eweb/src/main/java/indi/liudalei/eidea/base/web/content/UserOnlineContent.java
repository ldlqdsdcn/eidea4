package indi.liudalei.eidea.base.web.content;

import indi.liudalei.eidea.base.entity.bo.UserSessionBo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by 刘大磊 on 2017/1/3 14:05.
 * 在线用户
 */
public class UserOnlineContent {
    private static final Map<String, UserSessionBo> userOnlineMap = new HashMap<>();

    public static void addUser(String sessionId, UserSessionBo userSessionBo) {
        userOnlineMap.put(sessionId, userSessionBo);
    }

    public static void removeUserBySessionId(String sessionId) {
        userOnlineMap.remove(sessionId);
    }

    public static Set<UserSessionBo> getOnlineUsers() {
        return userOnlineMap.values().stream().collect(Collectors.toSet());
    }
}
