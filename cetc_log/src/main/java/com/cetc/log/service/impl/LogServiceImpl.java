package com.cetc.log.service.impl;

import com.cetc.common.core.entity.PageResult;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.log.dao.LogDao;
import com.cetc.log.feign.UserClient;
import com.cetc.log.service.LogService;
import com.cetc.model.admin.LoginSysUser;
import com.cetc.model.log.Log;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService {

    private final static Logger logger= LoggerFactory.getLogger(LogServiceImpl.class);

    @Autowired
    private LogDao logDao;

    @Autowired
    private UserClient userClient;

    @Override
    @Async
    public void save(Log log) {
        try {
            if(log.getFullname()==null){
                LoginSysUser sysUser = userClient.findByUsername(log.getUsername());
                if(sysUser!=null){
                 log.setFullname(sysUser.getFullname());
                }
            }
            logDao.save(log);
        }catch (Exception e){
            logger.error("日志存储失败：reason："+e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public Result queryPageslogs(Map<String, Object> params) {
        LoginSysUser loginSysUser = LoginUserUtil.getLoginSysUser();
        Result result=null;
        if("sysadmin".equals(loginSysUser.getUsername())||!loginSysUser.getType()){
            params.put("common",true);
            PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
            List<Log> logs = logDao.queryData(params);
            PageInfo<Log> logPageInfo = new PageInfo<>(logs);
            result = new Result(true, StatusCode.OK,"查询日志成功",logPageInfo);
        }else if (loginSysUser.getType()&&"auditadmin".equals(loginSysUser.getUsername())){
            params.put("auditadmin",true);
            PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
            List<Log> logs = logDao.queryData(params);
            PageInfo<Log> logPageInfo = new PageInfo<>(logs);
            result = new Result(true, StatusCode.OK,"查询日志成功",logPageInfo);
        }else {
            params.put("secadmin",true);
            PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
            List<Log> logs = logDao.queryData(params);
            PageInfo<Log> logPageInfo = new PageInfo<>(logs);
            result = new Result(true, StatusCode.OK,"查询日志成功",logPageInfo);
        }
        return result;
    }


}
