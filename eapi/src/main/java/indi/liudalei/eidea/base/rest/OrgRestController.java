package indi.liudalei.eidea.base.rest;

import indi.liudalei.eidea.api.model.Client;
import indi.liudalei.eidea.api.model.Org;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by 刘大磊 on 2017/4/13 14:20.
 */
@RestController
@Api(value = "Org", position = 1, description = "组织信息", consumes = "application/json")
public class OrgRestController {
    @ApiOperation(httpMethod = "GET",
            value = "获取组织信息",
            response = Org.class,
            nickname="/org/")
    @RequestMapping("/org/{id}")
    public Org getOrg(@PathVariable("id") Integer id) {
        Org org = new Org();
        org.setId(1);
        org.setName("鼎商动力");
        Client client = new Client();
        client.setCity("青岛");
        client.setAddress("青岛市南");
        client.setCreatedDate(new Date());
        client.setUpdatedDate(new Date());
        client.setId(1);
        org.setClient(client);
        return org;
    }
    @ApiOperation(value = "保存组织信息", httpMethod = "POST", response = String.class, notes = "保存组织信息",produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/org/save",method = RequestMethod.POST)
    public String saveOrg(@ApiParam(value = "要更新的组织", required = true)Org org)
    {
        return "更新组织信息成功";
    }
}
