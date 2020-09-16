package com.cetc.notice.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.model.notice.Notice;
import com.cetc.notice.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.util.Map;

@RestController
@RequestMapping("notice")
@Api(description = "通知控制层类接口")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping("findNotices/{isRead}")
    @ApiOperation("分页查询通知接口")
    public Result findNotices(@PathVariable int isRead, @RequestBody Map map){
        return noticeService.findPageNotice(isRead,map);
    }
    @PostMapping("saveNotice")
    @ApiOperation("保存通知接口")
    public Result saveNotice(@RequestBody Notice notice){
        return noticeService.saveNotice(notice);
    }

    @GetMapping("countMsgUnRead")
    @ApiOperation("统计未读的通知数接口")
    public  Result countMsgUnRead(){
        return noticeService.countMsgUnRead();
    }

    @GetMapping("markAsRead/{id}")
    @ApiOperation("根据通知id将通知标记为已读接口")
    public Result markAsRead(@PathVariable Long id){
        return noticeService.markAsRead(id);
    }

    @PostMapping("markAsReadByTime/{checker}")
    @ApiOperation("标记指定时间范围的消息为已读")
    public Result markAsReadByTime(@RequestBody Date[] range,@PathVariable String checker){
        return noticeService.markAsReadByTime(checker,range[0],range[1]);
    }

    @DeleteMapping("deleteNotice/{id}")
    @ApiOperation("根据id删除已读消息")
    public Result deleteNotice(@PathVariable Long id){

        return noticeService.deleteNotice(id) ;
    }
    @PostMapping("deleteByTime/{checker}")
    @ApiOperation("删除指定时间范围的已读消息")
    public Result deleteByTime(@RequestBody Date[] range,@PathVariable String checker){

        return noticeService.deleteByTime(checker,range[0],range[1]);
    }


}
