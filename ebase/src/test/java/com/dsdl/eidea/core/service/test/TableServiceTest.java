package com.dsdl.eidea.core.service.test;

import com.dsdl.eidea.core.dao.SearchColumnDao;
import com.dsdl.eidea.core.dao.TableDao;
import com.dsdl.eidea.core.dao.test.TableDaoTest;
import com.dsdl.eidea.core.entity.bo.SearchColumnBo;
import com.dsdl.eidea.core.entity.bo.TableBo;
import com.dsdl.eidea.core.entity.bo.TableColumnBo;
import com.dsdl.eidea.core.entity.bo.TableMetaDataBo;
import com.dsdl.eidea.core.entity.po.SearchColumnPo;
import com.dsdl.eidea.core.entity.po.TableColumnPo;
import com.dsdl.eidea.core.entity.po.TablePo;
import com.dsdl.eidea.core.service.TableService;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
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
        List<TableBo> tableBoList=tableService.findList(search);
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
