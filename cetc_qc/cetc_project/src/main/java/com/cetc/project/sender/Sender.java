package com.cetc.project.sender;

import com.cetc.model.notice.Notice;
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


    public void send(Notice notice){

       rabbitAmqTemplate.convertAndSend(queueName,notice);
    }
}
