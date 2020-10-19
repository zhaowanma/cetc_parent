package com.cetc.activiti.feign;

import com.cetc.activiti.feign.impl.UserClientImpl;
import com.cetc.common.core.entity.Result;
import com.cetc.model.admin.LoginSysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value="cetc-admin",fallbackFactory = UserClientImpl.class )
public interface UserClient {
    @GetMapping(value = "/users-anon/internal", params = "username")
    LoginSysUser findByUsername(@RequestParam("username") String username);

    @PostMapping("findUserByList")
     Result findUserByList(@RequestBody List<String> usernames);
}
