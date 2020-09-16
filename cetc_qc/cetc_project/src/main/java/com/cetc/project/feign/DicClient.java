package com.cetc.project.feign;

import com.cetc.common.core.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cetc-dic")
public interface DicClient {
    @GetMapping(value = "/dicValue/queryDicValuesByDicType/{dicType}",params = "dicType")
    Result findByName(@RequestParam("dicType")String dicType);
}
