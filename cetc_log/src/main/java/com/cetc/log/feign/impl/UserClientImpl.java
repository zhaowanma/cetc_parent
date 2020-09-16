package com.cetc.log.feign.impl;


import com.cetc.log.feign.UserClient;
import com.cetc.model.admin.LoginSysUser;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserClientImpl implements FallbackFactory<UserClient> {
    private static final Logger logger = LoggerFactory.getLogger(UserClientImpl.class);
    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            public LoginSysUser findByUsername(String username) {
                logger.info("fallback,原因：",throwable);
                return null;
            }
        };
    }
}
