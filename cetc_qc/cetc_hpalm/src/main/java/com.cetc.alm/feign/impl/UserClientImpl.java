package com.cetc.alm.feign.impl;
import com.cetc.alm.feign.UserClient;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.admin.LoginSysUser;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

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

            @Override
            public Result findUserByList(List<String> usernames) {
                logger.info("fallback,原因：",throwable);
                return new Result(false, StatusCode.OK,"服务降级");
            }
        };
    }
}
