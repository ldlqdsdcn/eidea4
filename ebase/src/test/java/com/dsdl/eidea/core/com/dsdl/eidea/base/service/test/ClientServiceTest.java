package com.dsdl.eidea.core.com.dsdl.eidea.base.service.test;

import com.dsdl.eidea.base.entity.bo.ClientBo;
import com.dsdl.eidea.base.service.ClientService;
import com.dsdl.eidea.core.params.QueryParams;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/13 9:55.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ClientServiceTest {
    private final Logger logger = Logger.getLogger(OrgServiceTest.class);
    @Autowired
    private ClientService clientService;

    @Test
    public void testFindClient() {
        Gson gson = new Gson();
        List<ClientBo> clientBoList = clientService.getClientList(new Search(),new QueryParams()).getData();
        logger.debug(gson.toJson(clientBoList));
        Assert.assertTrue(clientBoList.size() > 0);
    }
    @Test
    public void testGetClientById()
    {
        List<ClientBo> clientBoList = clientService.getClientList(new Search(),new QueryParams()).getData();
        ClientBo clientBo=clientService.getClientBo(clientBoList.get(0).getId());
        Gson gson=new Gson();
        System.out.println(gson.toJson(clientBoList.get(0)));
        Assert.assertNotNull(clientBo);
    }
}
