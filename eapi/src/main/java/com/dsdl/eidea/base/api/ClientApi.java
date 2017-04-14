package com.dsdl.eidea.base.api;

import com.dsdl.eidea.api.model.Client;
import com.dsdl.eidea.core.web.result.ClientApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by 刘大磊 on 2017/4/13 10:07.
 */
@RestController
@Api(value = "Client", position = 0, description = "实体信息", consumes = "application/json")
public class ClientApi {
    @ApiOperation(value = "根据实体ID获取实体", httpMethod = "GET", response = Client.class, notes = "根据实体的id信息获取实体的详情", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/client/{id}")
    public Client getClient(@PathVariable("id") Integer id) {
        Client client = new Client();
        client.setAddress("青岛市南");
        client.setCity("青岛");
        client.setCountry("中国");
        client.setCreatedDate(new Date());
        client.setUpdatedDate(new Date());
        return client;
    }

    @ApiOperation(value = "保存实体信息", httpMethod = "GET", response = Client.class, notes = "根据实体的id信息获取实体的详情", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/client/save")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "保存实体信息失败")})
    public ResponseEntity<Client> saveClient(Client client) {
        if(client==null||client.getName()==null)
        {
            ResponseEntity responseEntity=new ResponseEntity("实体信息不能为空",null,HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        return new ResponseEntity<Client>(new Client(),null,HttpStatus.OK);
    }
}
