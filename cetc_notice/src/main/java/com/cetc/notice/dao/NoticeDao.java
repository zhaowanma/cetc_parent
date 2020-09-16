package com.cetc.notice.dao;

import com.cetc.model.notice.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface NoticeDao {
     List<Notice> findNotice(@Param("checker") String checker,@Param("isRead") int isRead);

     void saveNotice(Notice notice);

     int countMsgIsRead(@Param("checker") String checker,@Param("isRead") int isRead);

     void markAsRead(Long id);

    void markAsReadByTime(@Param("checker")String checker,@Param("begin") Date begin,@Param("end") Date end);

    void deleteNotice(Long id);

    void deleteByTime(@Param("checker")String checker,@Param("begin") Date begin,@Param("end") Date end);

    List<Notice> query(@Param("checker")String checker,@Param("read")boolean read,@Param("begin") Date begin,@Param("end") Date end);


}
