package com.cetc.log.consumer;

import com.cetc.log.service.LogService;
import com.cetc.model.log.Log;
import com.cetc.model.log.constants.LogQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = LogQueue.LOG_QUEUE) // 监听队列
public class LogConsumer {
    private static final Logger logger = LoggerFactory.getLogger(LogConsumer.class);
    @Autowired
   private LogService logService;
    //日志处理方法，@RabbitHandler能够获取队列中的数据并封装到实体类中
   @RabbitHandler
    public void logHandler(Log log){
     try {
         //调用日志存储方法
        logService.save(log);

    }catch (Exception e){
     logger.info("日志保存失败 reason:"+e.getMessage());
    }
}
}
