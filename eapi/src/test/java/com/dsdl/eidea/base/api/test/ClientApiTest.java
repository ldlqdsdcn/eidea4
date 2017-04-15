package com.dsdl.eidea.base.api.test;

import com.dsdl.eidea.api.model.Client;
import com.dsdl.eidea.api.model.Org;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by 刘大磊 on 2017/4/14 9:34.
 */
@Slf4j
public class ClientApiTest {
    HttpHeaders requestHeaders = new HttpHeaders();

    private RestTemplate restTemplate=new RestTemplate();
    @Test
    public void testGetClient()
    {
        //HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
        HttpEntity<String> requestEntity = new HttpEntity<String>(createHeaders("paul","blue"));
        ResponseEntity<String> rss = restTemplate.exchange("http://localhost:8080/client/1", HttpMethod.GET, requestEntity, String.class);
        Gson gson=new Gson();
        System.out.println(gson.toJson(rss));
    }
    @Test
    public void testGetClientByGetForObject()
    {

    }

    private HttpHeaders createHeaders(final String username, final String password ){
        HttpHeaders headers =  new HttpHeaders();
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String( encodedAuth );
        headers.set( "Authorization", authHeader );

        headers.add("Content-Type", "application/xml");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return headers;
    }
}
