package com.cetc.alm.config.utils;

import com.alibaba.fastjson.JSON;
import com.cetc.alm.dao.AlmConfigDao;
import com.cetc.alm.service.AuthencateService;
import com.cetc.model.hpalm.AlmConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

@Component
public class CookieUtil {

    private static final Logger logger=LoggerFactory.getLogger( CookieUtil.class);

    @Autowired
    private AlmConfigDao almConfigDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AuthencateService authencateService;

    public synchronized List<String> getCookieList(){
             AlmConfig almConfig =almConfigDao.findAlmConfig();
             List<String> cookieList=null;
        try {
            if(redisUtil.get("alm_cookie_list")!=null){
                cookieList=(List)JSON.parseArray((String)redisUtil.get("alm_cookie_list"));
            }else{
               cookieList = authencateService.login(almConfig);
               if(cookieList!=null){
                   String s = JSON.toJSONString(cookieList);
                   redisUtil.setIfAbsent("alm_cookie_list",s,1800);
               }
            }
            return cookieList;

        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取cookieList失败");
        }
        return cookieList;
    }

}
