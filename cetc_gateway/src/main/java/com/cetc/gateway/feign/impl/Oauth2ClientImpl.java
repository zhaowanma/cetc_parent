package com.cetc.gateway.feign.impl;

import com.cetc.gateway.feign.Oauth2Client;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

//该类实现需实现FallbackFactory类，并覆写create方法
@Component
public class Oauth2ClientImpl implements FallbackFactory<Oauth2Client> {

    private static final Logger logger = LoggerFactory.getLogger(Oauth2ClientImpl.class);
    @Override
    public Oauth2Client create(Throwable throwable) {
        return new Oauth2Client() {
            @Override
            public Map<String, Object> postAccessToken(Map<String, String> parameters) {
               logger.info("fallback,reason：",throwable);
                return null;
            }
        };
    }
}
