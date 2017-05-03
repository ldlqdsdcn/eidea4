package com.dsdl.eidea.core.com.dsdl.eidea.base.service.test;

import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.entity.bo.UserBo;
import com.dsdl.eidea.base.entity.bo.UserSessionBo;
import com.dsdl.eidea.base.service.UserService;
import com.dsdl.eidea.base.service.UserSessionService;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 刘大磊 on 2016/12/27 8:17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserSessionService userSessionService;
    @Test
    public void testGetUserPrivileges() {
        Map<String, List<OperatorDef>> map = userService.getUserPrivileges(1);
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            System.out.println("--" + key);
            List<OperatorDef> operatorDefs = map.get(key);
            System.out.print("-------------->");
            for (OperatorDef operatorDef : operatorDefs) {
                System.out.print(operatorDef.getKey() + "|");
            }
            System.out.println();

        }

    }
//    @Test
//    public void testGetUsers()
//    {
//
//        List<UserBo> userBoList= userService.getUserList(new Search());
//
//        Gson gson=new Gson();
//        System.out.println(gson.toJson(userBoList.get(0)));
//        List<UserSessionBo> userSessionBoList=userSessionService.getUserSessionList(new Search());
//        System.out.println(gson.toJson(userSessionBoList.get(0)));
//    }
}
