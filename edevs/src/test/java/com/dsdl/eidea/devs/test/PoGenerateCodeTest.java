package com.dsdl.eidea.devs.test;

import com.dsdl.eidea.core.entity.bo.TableMetaDataBo;
import com.dsdl.eidea.core.service.TableService;
import com.dsdl.eidea.devs.model.GenModelDto;
import com.dsdl.eidea.devs.strategy.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Arrays;

/**
 * Created by 刘大磊 on 2017/1/11 10:44.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PoGenerateCodeTest {
    @Autowired
    private TableService tableService;

    @Test
    public void testGeneratePo() {
        GenModelDto genModelDto = TestData.getGenModel();
        TableMetaDataBo tableMetaDataBo = tableService.getTableDescription(genModelDto.getTableName());
        PoGenerateStrategy poGenerateStrategy = new PoGenerateStrategy(tableMetaDataBo, genModelDto);
        poGenerateStrategy.generateModel("D:\\dsdl\\code\\eidea\\base");

    }


    @Test
    public void testGenerateService() {
        GenModelDto genModelDto = TestData.getGenModel();
        TableMetaDataBo tableMetaDataBo = tableService.getTableDescription(genModelDto.getTableName());
        ServiceGenerateStrategy serviceGenerateStrategy=new ServiceGenerateStrategy(genModelDto,tableMetaDataBo);
        serviceGenerateStrategy.generateInterface("D:\\dsdl\\code\\eidea\\base");
        serviceGenerateStrategy.generateServiceclass("D:\\dsdl\\code\\eidea\\base");
    }
    @Test
    public void testGenerateController()
    {
        GenModelDto genModelDto = TestData.getGenModel();
        TableMetaDataBo tableMetaDataBo = tableService.getTableDescription(genModelDto.getTableName());
        ControllerGenerateStrategy generateStrategy=new ControllerGenerateStrategy(genModelDto,tableMetaDataBo);
        generateStrategy.generateController("D:\\dsdl\\code\\eidea\\eweb");
    }
    @Test
    public void testGenerateJsp()
    {
        GenModelDto genModelDto = TestData.getGenModel();
        TableMetaDataBo tableMetaDataBo = tableService.getTableDescription(genModelDto.getTableName());
        JspPageGenerateStrategy generateStrategy=new JspPageGenerateStrategy(genModelDto,tableMetaDataBo,tableService);
        generateStrategy.generateJspPage("D:\\dsdl\\code\\eidea\\eweb");
    }
}
