package com.cetc.alm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.alm.service.AuthencateService;
import com.cetc.model.hpalm.AlmConfig;
import com.jacob.com.Dispatch;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.*;

@Service
public class AuthencateServiceImpl implements AuthencateService {

    private final static Logger logger= LoggerFactory.getLogger(AuthencateServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<String> getCookie(AlmConfig almConfig) {
        try {
            String url = almConfig.getUrl();
            String username = almConfig.getUsername();
            String password = almConfig.getPassword();
            List<String> list = new ArrayList<>();
            if(url!=null&&!"".equals(url)&&username!=null&&!"".equals(username)&&password!=null&&!"".equals(password)){
                String authenticateUrl=url+"/authentication-point/authenticate";
                HttpHeaders httpHeaders = new HttpHeaders();
                String userDetails=username+":"+password;
                String base64=Base64.getEncoder().encodeToString(userDetails.getBytes("UTF-8"));
                httpHeaders.add("Authorization","Basic "+base64);
                HttpEntity<Object> request = new HttpEntity<>(null, httpHeaders);
                ResponseEntity<String> responseEntity = restTemplate.exchange(authenticateUrl, HttpMethod.GET, request, String.class);
                HttpHeaders headers = responseEntity.getHeaders();
                Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
                for (Map.Entry<String, List<String>> entry:entries) {
                    if ("Set-Cookie".equals(entry.getKey())) {
                        list = entry.getValue();
                        break;
                    }
                }
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("从alm中获取cookie失败,reason: "+e.getMessage());

        }
        return null;
    }

    @Override
    public List<String> getSession(AlmConfig almConfig,List<String> cookieList) {
        try {
            String url = almConfig.getUrl();
            String username = almConfig.getUsername();
            String password = almConfig.getPassword();
            if(url!=null&&!"".equals(url)&&username!=null&&!"".equals(username)&&password!=null&&!"".equals(password)){
                String sessionRestUrl=url+"/rest/site-session";
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.put(HttpHeaders.COOKIE,cookieList);
                HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
                ResponseEntity<String> requestEntity = restTemplate.postForEntity(sessionRestUrl, httpEntity, String.class);
                HttpHeaders resHeaders = requestEntity.getHeaders();
                Set<Map.Entry<String, List<String>>> entries = resHeaders.entrySet();
                for (Map.Entry<String, List<String>> entry:entries) {
                    if ("Set-Cookie".equals(entry.getKey())) {
                        cookieList = entry.getValue();
                        break;
                    }
                }
            }
            return cookieList;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("从alm中获取session失败,reason: "+e.getMessage());

        }
        return null;
    }

    @Override
    public Boolean closeSession(AlmConfig almConfig, List<String> cookieList) {
        try {
            String url = almConfig.getUrl();
            String username = almConfig.getUsername();
            String password = almConfig.getPassword();
            if(url!=null&&!"".equals(url)&&username!=null&&!"".equals(username)&&password!=null&&!"".equals(password)){
                String sessionRestUrl=url+"/rest/site-session";
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.put(HttpHeaders.COOKIE,cookieList);
                HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
                ResponseEntity<String> requestEntity = restTemplate.exchange(sessionRestUrl,HttpMethod.DELETE, httpEntity, String.class);
                System.out.println("成功关闭session");
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("从alm中关闭session失败,reason: "+e.getMessage());

        }
        return null;
    }

    @Override
    public List<String> login(AlmConfig almConfig) {
       try {
           List list = new ArrayList<>();
           List<String> cookieList = getCookie(almConfig);
           for (String cookie: cookieList) {
               list.add(cookie);
           }
           List<String> sessionList = getSession(almConfig, cookieList);
           for (String session: sessionList ) {
               list.add(session);
           }
           return list;
       }catch (Exception e){
           e.printStackTrace();
           logger.error("登录失败,reason: "+e.getMessage());
           throw new RuntimeException("alm登录失败");
       }
    }

    @Override
    public void logout(AlmConfig almConfig, List<String> cookieList) {
        try{

            String url = almConfig.getUrl();
            String username = almConfig.getUsername();
            String password = almConfig.getPassword();
            if(url!=null&&!"".equals(url)&&username!=null&&!"".equals(username)&&password!=null&&!"".equals(password)){
                url=url+"/authentication-point/logout";
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.put(HttpHeaders.COOKIE,cookieList);
                HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("alm退出失败,reason: "+e.getMessage());

        }
    }

    @Override
    public Map<String,String> findAlmUsers(Dispatch disp) {
        try {
            Map<String, String> almUsers = new HashMap<>();
            String user = Dispatch.call(disp, "GetAllUsers").getString();
            String s1 = XML.toJSONObject(user).toString();
            JSONObject j1 = JSON.parseObject(s1);
            JSONObject j2 = (JSONObject)j1.get("GetAllUsers");
            JSONArray j3 =(JSONArray)j2.get("TDXItem");
            for(int i=0;i<j3.size();i++) {
                JSONObject j4 =(JSONObject)j3.get(i);
                String userName = j4.get("USER_NAME").toString();
                String fullName = j4.get("FULL_NAME").toString();
                almUsers.put(userName,fullName);
            }
            return almUsers;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询alm中用户");
        }
    }

    @Override
    public void addUserProject(Dispatch disp, String domainName, String projectName, String userNames) {
        try {
            Dispatch.call(disp, "AddUsersToProject", domainName, projectName,userNames);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("为项目添加用户失败");
        }
    }

    @Override
    public void addUserGroup(Dispatch disp, String domainName, String projectName, String userNames) {
           try {
               Dispatch.call(disp, "AddUsersToGroup", domainName, projectName, "TDAdmin", userNames);
           }catch (Exception e){
               e.printStackTrace();
               throw new RuntimeException("为用户添加项目管理员权限失败");
           }
    }


}
