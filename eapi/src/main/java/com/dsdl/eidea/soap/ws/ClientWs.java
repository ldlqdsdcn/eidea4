package com.dsdl.eidea.soap.ws;

import com.dsdl.eidea.soap.model.Client;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/4/22 11:27.
 */
@WebService
public interface ClientWs {
    List<Client> getUserList();

    public String getHello(String name);
}
