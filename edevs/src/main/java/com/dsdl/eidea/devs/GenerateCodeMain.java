package com.dsdl.eidea.devs;

import com.dsdl.eidea.core.entity.bo.TableMetaDataBo;
import com.dsdl.eidea.core.service.TableService;
import com.dsdl.eidea.devs.model.BoInfo;
import com.dsdl.eidea.devs.model.GenModelDto;
import com.dsdl.eidea.devs.model.TableInfo;
import com.dsdl.eidea.devs.service.CodeGenerationService;
import com.dsdl.eidea.devs.strategy.PoGenerateStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/6 15:07.
 */
public class GenerateCodeMain implements CodeGenerationService {
    private TableService tableService;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        GenerateCodeMain codeMain = new GenerateCodeMain();
        codeMain.tableService = applicationContext.getBean(TableService.class);

    }

    @Override
    public void generateCode(List<GenModelDto> genModelDtoList) {
        for (GenModelDto genModelDto : genModelDtoList) {
            TableMetaDataBo tableMetaDataBo = tableService.getTableDescription(genModelDto.getTableName());
            PoGenerateStrategy poGenerateStrategy = new PoGenerateStrategy(tableMetaDataBo, genModelDto);
            poGenerateStrategy.generateModel();
        }

    }
}
