package indi.liudalei.eidea.core;

import indi.liudalei.eidea.core.entity.po.TableColumnPo;
import indi.liudalei.eidea.core.entity.po.TablePo;
import org.apache.commons.beanutils.BeanUtils;

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
