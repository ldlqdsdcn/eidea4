package com.dsdl.eidea.manager.service.impl;

import com.dsdl.eidea.core.service.LabelRmiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import com.dsdl.eidea.core.service.LabelService;

/**
 * Created by 刘大磊 on 2017/1/16 13:06.
 */
@Configuration
public class RmiServiceConfig {
    @Bean
    public RmiServiceExporter rmiLabelServiceExporter(LabelRmiService labelRmiService) {
        //SecurityManager sm = new SecurityManager();

        //System.setSecurityManager(sm);
        System.setProperty("java.rmi.server.codebase","localhost");
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setService(labelRmiService);
        rmiServiceExporter.setServiceName("labelRmiService");
        rmiServiceExporter.setServiceInterface(LabelRmiService.class);
        rmiServiceExporter.setRegistryPort(1999);
        return rmiServiceExporter;
    }
}
