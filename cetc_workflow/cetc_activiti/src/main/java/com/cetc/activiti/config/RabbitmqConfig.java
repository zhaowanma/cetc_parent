package com.cetc.activiti.config;

import com.cetc.model.activiti.constants.ActUserQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq配置
 * 
 * @author zwm
 *
 */
@Configuration
public class RabbitmqConfig {

	/**
	 * 声明队列
	 * 
	 * @return
	 */
	@Bean
	public Queue actUserQueue() {
		Queue queue = new Queue(ActUserQueue.ACTUSER_QUEUE);
		return queue;
	}


}
