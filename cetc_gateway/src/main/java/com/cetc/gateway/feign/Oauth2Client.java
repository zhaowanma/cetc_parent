package com.cetc.gateway.feign;

import com.cetc.gateway.feign.impl.Oauth2ClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "cetc-auth",fallbackFactory = Oauth2ClientImpl.class)
public interface Oauth2Client {

    @PostMapping(path = "/oauth/token")
    Map<String,Object> postAccessToken(@RequestParam Map<String, String> parameters);

}
