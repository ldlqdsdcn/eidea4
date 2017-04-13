package com.dsdl.eidea.base.api;

import com.dsdl.eidea.api.model.Client;
import com.dsdl.eidea.core.web.result.ApiResult;
import com.google.common.reflect.TypeToken;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by 刘大磊 on 2017/4/13 10:07.
 */
@RestController
@Api(value = "Client", position = 0,description = "实体信息",consumes="application/json")
public class ClientApi {
    @ApiOperation(value = "根据实体ID获取实体", httpMethod = "GET", response = Client.class, notes = "根据实体的id信息获取实体的详情",produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/client/{id}")
    public Client getClient(@PathVariable("id") Integer id)
    {
        Client client=new Client();
        client.setAddress("青岛市南");
        client.setCity("青岛");
        client.setCountry("中国");
        client.setCreatedDate(new Date());
        client.setUpdatedDate(new Date());
        return client;
    }
    @ApiOperation(value = "保存实体信息", httpMethod = "GET", response = Client.class, notes = "根据实体的id信息获取实体的详情",produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/client/save")
    public ApiResult<Client> saveClient(Client client)
    {
        new TypeToken<ApiResult<String>>(){}.getClass();
        return ApiResult.success(new Client());
    }
}
