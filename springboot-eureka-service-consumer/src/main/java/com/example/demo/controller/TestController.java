package com.example.demo.controller;

import com.example.demo.constant.Constants;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2018/4/1.
 */
@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping
    public String home() {
        return "Hello ServiceConsumerApplication";
    }

    @GetMapping("/discovery.html")
    @HystrixCommand(fallbackMethod = "fallbackMethodName")
    public String doDiscoveryService() {
        String providerMsg = restTemplate.getForEntity(Constants.RAW_REST_SERVER_URL + "/index.html",
                String.class).getBody();
        return "来自 service provider的消息：" + providerMsg;
    }

    public String fallbackMethodName() {
        return "ERROR";
    }

}
