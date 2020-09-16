package com.cetc.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;


//在启动类上添加注解@EnableZuulProxy,声明一个zuul代理。
// 该代理事宜Ribbon来定位注册在Eureka Server中的微服务，
// 同时该代理还整合了Hystrix,从而实现容错，所以经过zuul的请求都会被Hystrix保护起来

@SpringBootApplication
@EnableZuulProxy
@EnableFeignClients
public class ZuulServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class);
    }



}
