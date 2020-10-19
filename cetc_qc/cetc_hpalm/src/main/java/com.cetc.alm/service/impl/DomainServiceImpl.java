package com.cetc.alm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.alm.dao.AlmConfigDao;
import com.cetc.alm.dao.AlmSiteConfigDao;
import com.cetc.alm.dao.CodeDao;
import com.cetc.alm.service.AuthencateService;
import com.cetc.alm.service.DomainService;
import com.cetc.alm.utils.SiteAdminUtils;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.hpalm.AlmConfig;
import com.cetc.model.project.Code;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomainServiceImpl implements DomainService {

    private static final Logger logger= LoggerFactory.getLogger(DomainServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private AlmConfigDao almConfigDao;

    @Autowired
    private AlmSiteConfigDao almSiteConfigDao;

    @Autowired
    private AuthencateService authencateService;

    @Autowired
    private CodeDao codeDao;

    @Override
    public Result findDomains(AlmConfig almConfig,List<String> cookieList) {
        List<String> domainList = new ArrayList<>();
      try {
          if(cookieList!=null&&cookieList.size()>0){
              String url = almConfig.getUrl();
              String username = almConfig.getUsername();
              String password = almConfig.getPassword();
              if(url!=null&&!"".equals(url)&&username!=null&&!"".equals(username)&&password!=null&&!"".equals(password)){
                  String domainRestUrl=url+"/rest/domains/";
                  HttpHeaders httpHeaders = new HttpHeaders();
                  List<MediaType> mediaTypes = new ArrayList<>();
                  mediaTypes.add(MediaType.APPLICATION_JSON);
                  httpHeaders.setAccept(mediaTypes);
                  httpHeaders.put(HttpHeaders.COOKIE,cookieList);
                  HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
                  ResponseEntity<String> response = restTemplate.exchange(domainRestUrl, HttpMethod.GET, httpEntity, String.class);
                  String domainJsonStr=response.getBody();
                  JSONObject jsonObject = JSON.parseObject(domainJsonStr);
                  Object object = jsonObject.get("Domain");

                  if(object instanceof JSONArray){
                      JSONArray domainArray=(JSONArray) object;
                      for(int i=0;i<domainArray.size();i++){
                          JSONObject domainObj=(JSONObject)domainArray.get(i);
                          String domainName=(String)domainObj.get("Name");
                          if(!"DEFAULT".equals(domainName)){
                              domainList.add(domainName);
                          }
                      }
                  }
                  if(object instanceof JSONObject){
                      JSONObject domainObject=(JSONObject) object;
                      String domainName=(String)domainObject.get("Name");
                      if(!"DEFAULT".equals(domainName)) {
                          domainList.add(domainName);
                      }
                  }
              }
          }
          return new Result(true, StatusCode.OK,"查询所有的域成功",domainList);
      }catch (Exception e){
              e.printStackTrace();
              logger.error("查询所有alm的域失败,reason: "+e.getMessage());
              return new Result(false,StatusCode.ERROR,"查询所有的域失败");
      }
    }

    @Override
    public Result findAlmDomains() {
        List<String> cookieList =null;
        AlmConfig almConfig=almConfigDao.findAlmConfig();
        try {
           cookieList =authencateService.login(almConfig) ;
            if(cookieList!=null&&cookieList.size()>0){
                Result domains = findDomains(almConfig, cookieList);
                return domains;
            }
            return new Result(false,StatusCode.ERROR,"失败");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询alm中所有的域失败");
            return new Result(false,StatusCode.ERROR,"失败");
        }finally {
            if(cookieList!=null){
                authencateService.closeSession(almConfig,cookieList);
                authencateService.logout(almConfig,cookieList);
            }

        }
    }

    @Override
    public Result createAlmDomains(long codeId) {
        Code code = codeDao.findOne(codeId);
        String almDomainName=code.getScope();
        Dispatch disp=null;
        try {
           disp = SiteAdminUtils.getDisp(almSiteConfigDao.findAlmSiteConfig());
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"登录失败，请检查是否有空闲license");
        }
        try {
            Result almDomainsResult = findAlmDomains();
            List<String> almDomains=new ArrayList<>();
            if(almDomainsResult.isFlag()){
               almDomains=(List<String>)almDomainsResult.getData();
            }
            if(!almDomains.contains(almDomainName)){
                System.out.println(almDomainName);
                Dispatch.call(disp,"CreateDomain",almDomainName,"","",-1);
            }
           return new Result(true,StatusCode.OK,"alm域创建成功");
        }catch (Exception e){
           e.printStackTrace();
           logger.error("***************创建alm域"+almDomainName+"失败"+e.getMessage());
            return new Result(false,StatusCode.ERROR,e.getMessage());
        }finally {
            if(disp!=null){
                Dispatch.call(disp, "Logout");
                ComThread.InitSTA();
            }
        }
    }


}
