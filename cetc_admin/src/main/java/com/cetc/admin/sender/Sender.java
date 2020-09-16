package com.cetc.admin.sender;

import com.cetc.model.admin.SysUser;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Autowired
    private AmqpTemplate rabbitAmqTemplate;
    @Value("${mq.queue.name}")
    private String queueName;


    public void send(SysUser sysUser){

       rabbitAmqTemplate.convertAndSend(queueName,sysUser);
    }
}
