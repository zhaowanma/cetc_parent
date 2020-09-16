package com.cetc.notice.consumer;


import com.cetc.model.notice.Notice;
import com.cetc.model.notice.constants.NoticeQueue;
import com.cetc.notice.dao.NoticeDao;
import com.cetc.notice.service.NoticeService;
import com.cetc.notice.utils.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = NoticeQueue.NOTICE_QUEUE)
public class NoticeConsumer {
    private static final Logger logger = LoggerFactory.getLogger(NoticeConsumer.class);

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private NoticeService noticeService;
    @RabbitHandler
    public void noticeHandler(Notice notice){
            noticeService.saveNotice(notice);
        try {

            webSocketService.sendChatMessage(notice);
            int i = noticeDao.countMsgIsRead(notice.getChecker(), 0);
            webSocketService.sendChatMessageNums(i,notice.getChecker());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("socket发送消息失败 reason:"+e.getMessage());
        }


    }
}
