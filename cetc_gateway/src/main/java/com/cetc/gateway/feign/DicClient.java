package com.cetc.gateway.feign;
import com.cetc.common.core.entity.Result;
import com.cetc.gateway.feign.impl.DicClientImpl;
import com.cetc.gateway.feign.impl.UserClientImpl;
import com.cetc.model.admin.LoginSysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="cetc-dic",fallbackFactory = DicClientImpl.class )
public interface DicClient {
    @GetMapping(value = "/params/queryByKey/{key}", params = "key")
    Result queryByKey(@PathVariable String key);


}
