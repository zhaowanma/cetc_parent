package com.cetc.project.feign;

import com.cetc.model.admin.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cetc-admin")
public interface UserClient {
    @GetMapping(value = "/users-anon/internal",params = "username")
    SysUser findByUsername(@RequestParam("username")String username);
}
