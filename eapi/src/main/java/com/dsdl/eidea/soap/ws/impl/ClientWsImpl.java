package com.dsdl.eidea.soap.ws.impl;

import com.dsdl.eidea.soap.model.Client;
import com.dsdl.eidea.soap.ws.ClientWs;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/4/22 11:29.
 */
@WebService(endpointInterface = "com.dsdl.eidea.soap.ws.ClientWs")
public class ClientWsImpl implements ClientWs {

    @Override
    public List<Client> getUserList() {
        Client client = new Client();
        client.setName("鼎商动力");
        client.setAddress("市南区");
        List<Client> clientList = new ArrayList<>();
        return clientList;
    }

    @Override
    public String getHello(String name) {
        System.out.println("-------------------------->" + name);
        return "Hello" + name;
    }
}
