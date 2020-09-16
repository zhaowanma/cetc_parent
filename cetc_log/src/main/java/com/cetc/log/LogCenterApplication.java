package com.cetc.log;

import com.cetc.common.core.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableFeignClients
public class LogCenterApplication {

	@Bean
	public IdWorker getIdWorker(){
		return new IdWorker(0,0);
	}

	public static void main(String[] args) {
		SpringApplication.run(LogCenterApplication.class, args);
	}

}