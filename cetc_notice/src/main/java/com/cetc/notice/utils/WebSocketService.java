package com.cetc.notice.utils;

import com.cetc.model.notice.Notice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendChatMessage(Notice notice){
        try {
            simpMessagingTemplate.convertAndSend("/chat/notice/"+notice.getChecker(),
                    "您有一个"+notice.getTitle()+"...");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("WebSocketService"+e.getMessage());
        }
    }

    public void sendChatMessageNums(int nums,String checker){
        try {
            simpMessagingTemplate.convertAndSend("/chat/nums/"+checker,
                    nums);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("WebSocketService"+e.getMessage());
        }
    }
}
