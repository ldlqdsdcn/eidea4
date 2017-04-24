package com.dsdl.eidea.core.init;

import com.dsdl.eidea.core.rmi.ReportSettingsRmi;
import com.dsdl.eidea.core.service.ReportSettingsService;
import com.dsdl.setting.RmiSettingHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * Created by 刘大磊 on 2017/4/24 16:03.
 */
@Configuration
public class RmiServerConfig {
    /**
     *
     * @param reportSettingsRmi
     * @return
     */
    @Bean
    public RmiServiceExporter reportSettingsServiceExporter(ReportSettingsRmi reportSettingsRmi) {
        return registerRmiService("reportSettingsRmi", ReportSettingsRmi.class, reportSettingsRmi);
    }
    /**
     * @param name       远程对象名
     * @param classz     接口类
     * @param springBean springBean对象
     * @return
     */
    private RmiServiceExporter registerRmiService(String name, Class classz, Object springBean) {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setService(springBean);
        rmiServiceExporter.setServiceName(name);
        rmiServiceExporter.setServiceInterface(classz);
        rmiServiceExporter.setRegistryPort(RmiSettingHelper.getMainPort());
        return rmiServiceExporter;
    }
}
