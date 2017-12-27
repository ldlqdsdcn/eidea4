package indi.liudalei.eidea.report.base.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2017/4/24 15:40.
 */
@Getter
@Setter
public class UserSession {
    private Integer id;
    private String sessionId;
    private String loginDate;
    private String remoteHost;
}
