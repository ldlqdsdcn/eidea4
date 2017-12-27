package indi.liudalei.eidea.core.web.util;

import indi.liudalei.eidea.base.entity.po.FileSettingPo;
import indi.liudalei.eidea.base.service.FileSettingService;
import indi.liudalei.eidea.base.service.SpringContextHolder;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 刘大磊 on 2017/5/16 10:54.
 */
public class HttpUtil {
    private static ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
    private static FileSettingService fileSettingService=applicationContext.getBean(FileSettingService.class);

    public static FileSettingPo getModuleByServletRequest(HttpServletRequest request)
    {
       return  fileSettingService.getFileSettingsByRequestPath(request.getServletPath());
    }
}
