package com.cetc.authentication.feign;


import com.cetc.authentication.feign.impl.UserClientImpl;
import com.cetc.model.admin.LoginSysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="cetc-admin",fallbackFactory = UserClientImpl.class )
public interface UserClient {
    @GetMapping(value = "/users-anon/internal", params = "username")
    LoginSysUser findByUsername(@RequestParam("username") String username);


}
