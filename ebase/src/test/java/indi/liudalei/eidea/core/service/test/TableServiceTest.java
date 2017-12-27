package indi.liudalei.eidea.core.service.test;

import indi.liudalei.eidea.core.dao.SearchColumnDao;
import indi.liudalei.eidea.core.dao.test.TableDaoTest;
import indi.liudalei.eidea.core.entity.bo.TableBo;
import indi.liudalei.eidea.core.entity.bo.TableMetaDataBo;
import indi.liudalei.eidea.core.entity.po.SearchColumnPo;
import indi.liudalei.eidea.core.entity.po.TablePo;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.service.TableService;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/6 15:08.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TableServiceTest {
    private static final Logger logger=Logger.getLogger(TableDaoTest.class);
    @Autowired
    private TableService tableService;
    @Autowired
    private SearchColumnDao searchColumnDao;

    @Test
    public void testGetTableInfo()
    {
        TableMetaDataBo tableMetaDataBo=tableService.getTableDescription("core_table");
        Gson gson=new Gson();
        System.out.println(gson.toJson(tableMetaDataBo));
    }
    @Test
    @Transactional
    public void testFindList()
    {
        Search search=new Search(TablePo.class);
       // search.addField("tableName", Filter.OP_LIKE,"core_table");
        search.addFilter(Filter.like("tableName","core_table"));
        List<TableBo> tableBoList=tableService.findList(search,new QueryParams()).getData();
        logger.debug(tableBoList.size());
    }
    @Test
    @Transactional
    public void testGetTableBoById()
    {
        TableBo tableBo=tableService.getTableBo(1);
        System.out.println(tableBo.getName());
    }
    @Test
    @Transactional
    public void selectTableColumnList()
    {
        Search search=new Search();
        search.addFilterEqual("coreSearch.id",3);
       List<SearchColumnPo>  tableColumnPoList=searchColumnDao.search(search);
        System.out.println(tableColumnPoList.size());
    }
}
