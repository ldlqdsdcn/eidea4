package com.dsdl.eidea.base.api.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 刘大磊 on 2017/4/14 9:34.
 */
@Slf4j
public class ClientApiTest {
    HttpHeaders requestHeaders = new HttpHeaders();

    private RestTemplate restTemplate=new RestTemplate();
    @Test
    public void testSaveClient()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("username","paul");
        headers.add("password","blue");
        //HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> rss = restTemplate.exchange("http://localhost:8080/client/1", HttpMethod.GET, requestEntity, String.class, "");
    }
}
