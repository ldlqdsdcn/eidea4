package com.dsdl.eidea.core;

import com.dsdl.eidea.base.entity.po.UserPo;
import com.dsdl.eidea.core.entity.po.TableColumnPo;
import com.dsdl.eidea.core.entity.po.TablePo;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Set;

/**
 * Created by 刘大磊 on 2016/12/22 17:21.
 */
public class BeanUtilTest {
    public static void main(String[] args)throws Exception
    {
        TableColumnPo tableColumnPo=new TableColumnPo();
        TablePo tablePo=new TablePo();
        tablePo.setName("测试");
        tableColumnPo.setTablePo(tablePo);
       String value= BeanUtils.getProperty(tableColumnPo,"tablePo.name");
       System.out.println(value);
    }
}
