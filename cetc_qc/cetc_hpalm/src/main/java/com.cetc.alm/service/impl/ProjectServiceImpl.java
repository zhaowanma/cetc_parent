package com.cetc.alm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.alm.config.utils.CookieUtil;
import com.cetc.alm.dao.AlmConfigDao;
import com.cetc.alm.service.AuthencateService;
import com.cetc.alm.service.ProjectService;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.hpalm.AlmConfig;
import com.cetc.model.hpalm.AlmProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static final Logger logger= LoggerFactory.getLogger(ProjectServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AlmConfigDao almConfigDao;
    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private AuthencateService authencateService;
    @Override
    public Result findProjects(AlmConfig almConfig, String domainName,List<String> cookieList) {
        List<String> projectList = new ArrayList<>();
        try {
            if(cookieList!=null&&cookieList.size()>0){
                String url = almConfig.getUrl();
                String username = almConfig.getUsername();
                String password = almConfig.getPassword();
                if(url!=null&&!"".equals(url)&&username!=null&&!"".equals(username)&&password!=null&&!"".equals(password)){
                    url=url+"/rest/domains/"+domainName+"/projects";
                    List<MediaType> mediaTypes = new ArrayList<>();
                    mediaTypes.add(MediaType.APPLICATION_JSON);
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.put(HttpHeaders.COOKIE,cookieList);
                    httpHeaders.setAccept(mediaTypes);
                    HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
                    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
                    String projectJsonStr=response.getBody();
                    JSONObject jsonObject = JSON.parseObject(projectJsonStr);
                    Object object = jsonObject.get("Project");

                    if(object instanceof JSONArray){
                        JSONArray projectArray=(JSONArray) object;
                        for(int i=0;i<projectArray.size();i++){
                            JSONObject domainObj=(JSONObject)projectArray.get(i);
                            String projectName=(String)domainObj.get("Name");
                                projectList.add(projectName);
                        }
                    }
                    if(object instanceof JSONObject){
                        JSONObject projectObject=(JSONObject) object;
                        String projectName=(String)projectObject.get("Name");
                            projectList.add(projectName);
                    }
                }
            }
            return new Result(true, StatusCode.OK,"根据域名查询项目成功",projectList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("根据域名查询项目失败,reason: "+e.getMessage());
            return new Result(false, StatusCode.ERROR,"根据域名查询项目失败");
        }
    }

    @Override
    public Result findProjectsByDomainName(String domainName) {
             AlmConfig almConfig=null;
             List<String> login=null;
        try {
            almConfig = almConfigDao.findAlmConfig();
            login = cookieUtil.getCookieList();
            Result projects = findProjects(almConfig, domainName, login);
            return new Result(true,StatusCode.OK,"ok",projects.getData());
        }catch (Exception e){
            logger.error("根据域名查询项目失败");
            return new Result(false,StatusCode.ERROR,"error");

        }finally {
             authencateService.logout(almConfig,login);
        }

    }



}
