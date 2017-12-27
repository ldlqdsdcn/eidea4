package indi.liudalei.eidea.core.dao.test;

import indi.liudalei.eidea.core.entity.bo.UniqueIndexBo;
import indi.liudalei.eidea.core.dao.TableDao;
import indi.liudalei.eidea.core.entity.po.TablePo;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/6 10:03.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TableDaoTest {
    private static Logger logger= Logger.getLogger(TableDaoTest.class);
    @Autowired
    private TableDao tableDao;
    @Test
    public void testGetPrimaryKey()
    {
        String pk=tableDao.getPrimaryKey("core_table");
        Assert.assertEquals("id",pk);
    }
    @Test
    public void testGetUniqueIndex()
    {
        List<UniqueIndexBo> uniqueIndexBoList=tableDao.getUniqueIndex("core_table");
        for(UniqueIndexBo uniqueIndexBo:uniqueIndexBoList)
        {
            logger.info("uniqueIndexBo:"+uniqueIndexBo.getIndexColumnName()+" "+uniqueIndexBo.getIndexName());
        }
        logger.info("uniqueIndexBoList.size="+uniqueIndexBoList.size());
        Gson gson=new Gson();

    }
    @Test
    @Transactional
    public void testGetTablePo()
    {
       TablePo tablePo= tableDao.find(3);
       // tablePo.getCoreTableColumns();
        Gson gson=new Gson();
        System.out.println(tablePo);
    }
}
