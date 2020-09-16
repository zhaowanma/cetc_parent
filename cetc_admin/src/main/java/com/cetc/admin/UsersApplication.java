package com.cetc.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.elasticsearch.ElasticSearchRestHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/*
* @EnableResourceServer用于告诉微服务，它是一个受保护的资源,该注解强制执行一个过滤器，该过滤器会拦截对该服务的
* 所有传入调用，检查传入的HTTP首部中是否存在OAuth2访问令牌，然后调用security.oauth2.resource.userInfi中定义的
* 回调URL来查看令牌是否有效。
* */
@SpringBootApplication
@EnableFeignClients
public class UsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class,args);
    }



}
