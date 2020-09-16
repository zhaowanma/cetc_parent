package com.cetc.notice.service.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.model.admin.LoginSysUser;
import com.cetc.model.notice.Notice;
import com.cetc.notice.dao.NoticeDao;
import com.cetc.notice.service.NoticeService;
import com.cetc.notice.utils.WebSocketService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class NoticeServiceImpl implements NoticeService {

    private final static Logger log = LoggerFactory.getLogger(NoticeServiceImpl.class);

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public Result findPageNotice(int isRead, Map params) {
        LoginSysUser loginSysUser = LoginUserUtil.getLoginSysUser();
        PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
        List<Notice> notices = noticeDao.findNotice(loginSysUser.getUsername(), isRead);
        PageInfo<Notice> pageInfo = new PageInfo<>(notices);

        return new Result(true, StatusCode.OK,"查询通知成功",pageInfo);
    }

    @Override
    @Transactional
    public Result saveNotice(Notice notice) {
        noticeDao.saveNotice(notice);
        return new Result(true,StatusCode.OK,"保存通知成功");
    }

    @Override
    public Result countMsgUnRead() {
        String username = LoginUserUtil.getLoginSysUser().getUsername();
        int i = noticeDao.countMsgIsRead(username, 0);
        return new Result(true,StatusCode.OK,"OK",i);
    }
    @Override
    public Result markAsRead(Long id) {
        noticeDao.markAsRead(id);
        String username = LoginUserUtil.getLoginSysUser().getUsername();
        int i = noticeDao.countMsgIsRead(username, 0);
        webSocketService.sendChatMessageNums(i,username);
        return new Result(true,StatusCode.OK,"成功标记为已读");
    }

    @Override
    public Result markAsReadByTime(String checker,Date begin, Date end) {
        noticeDao.markAsReadByTime(checker,begin,end);
        return new Result(true,StatusCode.OK,"");
    }

    @Override
    public Result deleteNotice(Long id) {
        noticeDao.deleteNotice(id);
        return new Result(true,StatusCode.OK,"");
    }

    @Override
    @Transactional
    public Result deleteByTime(String checker,Date begin, Date end) {
        noticeDao.deleteByTime(checker,begin,end);
        return new Result(true,StatusCode.OK,"");
    }

}
