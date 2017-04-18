package com.dsdl.eidea.devs;

import com.dsdl.eidea.core.entity.bo.TableMetaDataBo;
import com.dsdl.eidea.core.service.LabelService;
import com.dsdl.eidea.core.service.LanguageService;
import com.dsdl.eidea.core.service.MessageService;
import com.dsdl.eidea.core.service.TableService;
import com.dsdl.eidea.devs.model.GenModelDto;
import com.dsdl.eidea.devs.model.GenSettings;
import com.dsdl.eidea.devs.service.CodeGenerationService;
import com.dsdl.eidea.devs.strategy.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/6 15:07.
 * 项目代码输出文件入口
 */
public class GenerateCodeMain implements CodeGenerationService {
    private TableService tableService;
    private LanguageService languageService;
    private LabelService labelService;
    private MessageService messageService;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        GenerateCodeMain codeMain = new GenerateCodeMain();
        codeMain.tableService = applicationContext.getBean(TableService.class);
        codeMain.labelService = applicationContext.getBean(LabelService.class);
        codeMain.messageService = applicationContext.getBean(MessageService.class);
        codeMain.languageService = applicationContext.getBean(LanguageService.class);
        GenModelDto genModelDto = new GenModelDto("core_report_settings", "ReportSettings", "core", "报表设置");
        genModelDto.setBasePackage("com.dsdl.eidea");
        List<GenModelDto> list = new ArrayList<>();
        list.add(genModelDto);

        GenSettings genSettings = new GenSettings();
        /**
         * 设置项目跟目录
         */
        genSettings.setRootPath("D:\\dsdl\\code\\eidea4\\");
        /**
         * 设置 controller 和 jsp文件输出项目位置
         */
        genSettings.setControllerPath(genSettings.getRootPath() + "eweb");
        /**
         * 设置业务逻辑代码输出项目位置
         */
        genSettings.setOutputPath(genSettings.getRootPath() + "ebase");
        genSettings.setGenModelDtoList(list);
        codeMain.generateCode(genSettings);
        System.out.println("执行完毕");
        System.exit(0);
    }


    public void generateCode(GenSettings genSettings) {

        for (GenModelDto genModelDto : genSettings.getGenModelDtoList()) {
            TableMetaDataBo tableMetaDataBo = tableService.getTableDescription(genModelDto.getTableName());
            /**
             * 生成PO代码
             */
            PoGenerateStrategy poGenerateStrategy = new PoGenerateStrategy(tableMetaDataBo, genModelDto);
            poGenerateStrategy.generateModel(genSettings.getOutputPath());
            System.out.println("生成Po类完成");

            ServiceGenerateStrategy serviceGenerateStrategy = new ServiceGenerateStrategy(genModelDto, tableMetaDataBo);
            serviceGenerateStrategy.generateInterface(genSettings.getOutputPath());
            serviceGenerateStrategy.generateServiceclass(genSettings.getOutputPath());
            /**
             * 生成Controller 部分代码
             */
            ControllerGenerateStrategy controllerGenerateStrategy = new ControllerGenerateStrategy(genModelDto, tableMetaDataBo);
            controllerGenerateStrategy.generateController(genSettings.getControllerPath());
            /**
             * 生成Jsp页面
             */
            JspPageGenerateStrategy jspPageGenerateStrategy = new JspPageGenerateStrategy(genModelDto, tableMetaDataBo, tableService);
            jspPageGenerateStrategy.generateJspPage(genSettings.getControllerPath());
            /**
             * 生成label标签
             */
            I18NGenerateStrategy i18NGenerateStrategy = new I18NGenerateStrategy(genModelDto, tableMetaDataBo, languageService, messageService, labelService);
            i18NGenerateStrategy.generateLabel();
        }

    }
}
