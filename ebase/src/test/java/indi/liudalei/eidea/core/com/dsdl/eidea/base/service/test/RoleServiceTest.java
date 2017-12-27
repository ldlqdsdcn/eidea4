package indi.liudalei.eidea.core.com.dsdl.eidea.base.service.test;

import java.util.List;

import indi.liudalei.eidea.core.params.QueryParams;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import indi.liudalei.eidea.base.entity.bo.RoleBo;
import indi.liudalei.eidea.base.service.RoleService;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RoleServiceTest {
	    private Logger logger= Logger.getLogger(RoleServiceTest.class);
	    @Autowired
	    private RoleService roleService;
	    @Test
	    public void testgetLabelList(){
		        Gson gson = new Gson();
		        List<RoleBo> roleBoList = roleService.getRoleList(new Search(),new QueryParams()).getData();
		        logger.debug(gson.toJson(roleBoList));
		        Assert.assertTrue(roleBoList.size() > 0);
	    }

	    
	    
}
