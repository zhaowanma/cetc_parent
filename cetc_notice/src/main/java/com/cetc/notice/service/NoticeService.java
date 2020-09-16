package com.cetc.notice.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.notice.Notice;
import java.util.Date;
import java.util.Map;

public interface NoticeService {

     Result findPageNotice(int isRead, Map map);

     Result saveNotice(Notice notice);

     Result countMsgUnRead();

     Result markAsRead(Long id);

    Result markAsReadByTime(String creater,Date begin,Date end);

    Result deleteNotice(Long id);

    Result deleteByTime(String creater,Date begin,Date end);

}
