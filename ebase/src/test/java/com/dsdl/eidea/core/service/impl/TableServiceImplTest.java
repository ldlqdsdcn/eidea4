package com.dsdl.eidea.core.service.impl;

import com.dsdl.eidea.core.entity.bo.TableMetaDataBo;
import com.dsdl.eidea.core.service.TableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by 车东明 on 2017/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TableServiceImplTest {
    @Autowired
    private TableService tableService;
    @Test
    public void getTableDescription() throws Exception {
            String tableName = "sys_user";
            TableMetaDataBo tableMetaDataBo=tableService.getTableDescription(tableName);
            System.out.println(tableMetaDataBo);

    }
    @Test
    public void  findExistTableByName() throws Exception{
        String name="sys_user";
        boolean a=tableService.findExistTableByName(name);
        System.out.println(a);
    }

}