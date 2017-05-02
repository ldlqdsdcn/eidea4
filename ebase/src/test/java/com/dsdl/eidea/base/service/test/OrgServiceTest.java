package com.dsdl.eidea.base.service.test;

import com.dsdl.eidea.base.entity.bo.OrgBo;
import com.dsdl.eidea.base.entity.po.OrgPo;
import com.dsdl.eidea.base.service.OrgService;
import com.googlecode.genericdao.search.Search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/2/16 8:15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OrgServiceTest {
//    @Autowired
//    private OrgService orgService;
//    @Test
//    public void testGetOrg()
//    {
//        orgService.getOrgBo(1);
//    }
//    @Test
//    public  void TestGetOrgInit()
//    {
//        OrgPo orgPo=orgService.getOrg(1);
//        System.out.println(orgPo.getSysClient().getSysOrgs().size());
//    }
//    @Test
//    public void testGetOrgs()
//    {
//       List<OrgBo> orgBoList= orgService.findOrgList(new Search());
//        System.out.println(orgBoList.size());
//    }

}
