package com.dsdl.eidea.core.com.dsdl.eidea.base.service.test;

import com.dsdl.eidea.base.service.OrgService;
import com.googlecode.genericdao.search.Search;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 刘大磊 on 2016/12/12 18:00.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OrgServiceTest {
    private final Logger logger=Logger.getLogger(OrgServiceTest.class);
    @Autowired
    private OrgService orgService;
    @Test
    public void testSearchOrg()
    {
        logger.debug("first search");
        orgService.findOrgList(new Search());
        logger.debug("second search");
        orgService.findOrgList(new Search());
    }
}
