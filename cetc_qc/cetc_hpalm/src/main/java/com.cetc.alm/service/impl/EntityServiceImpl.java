package com.cetc.alm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.alm.service.AuthencateService;
import com.cetc.alm.service.EntityService;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.hpalm.AlmConfig;
import com.cetc.model.hpalm.EntityField;
import com.cetc.model.hpalm.EntityFieldListValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntityServiceImpl implements EntityService {
    private static final Logger logger= LoggerFactory.getLogger(EntityServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Result findEntityFields(String domainName, String projectName, String entityName, AlmConfig almConfig,List<String> cookieList) {
        List<EntityField> entityList = new ArrayList<>();
        try {
            if(cookieList!=null&&cookieList.size()>0){
                String url = almConfig.getUrl();
                String username = almConfig.getUsername();
                String password = almConfig.getPassword();
                if(url!=null&&!"".equals(url)&&username!=null&&!"".equals(username)&&password!=null&&!"".equals(password)){
                    url=url+"/rest/domains/"+domainName+"/projects/"+projectName+"/customization/entities/"+entityName+"/fields";
                    List<MediaType> mediaTypes = new ArrayList<>();
                    mediaTypes.add(MediaType.APPLICATION_JSON);
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.put(HttpHeaders.COOKIE,cookieList);
                    httpHeaders.setAccept(mediaTypes);
                    HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
                    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
                    String entityFieldJsonStr=response.getBody();
                    System.out.println(entityFieldJsonStr);
                    JSONObject jsonObject = JSON.parseObject(entityFieldJsonStr);
                    JSONObject jsonObject2 = (JSONObject)jsonObject.get("Fields");
                    JSONArray fields = (JSONArray)jsonObject2.get("Field");
                    for(int i=0;i<fields.size();i++){
                        String s = fields.get(i).toString();
                        EntityField entityField = JSON.parseObject(s, EntityField.class);
                        entityList.add(entityField);
                    }
                }
            }
            return new Result(true, StatusCode.OK,"查询实体字段成功",entityList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询alm实体字段失败,reason: "+e.getMessage());
            throw new RuntimeException("查询alm实体字段失败");
        }
    }

    @Override
    public Result queryEntityListValues(String domainName, String projectName, String entityName, AlmConfig almConfig, List<String> cookieList) {
        Map<Object, Object> map = new HashMap<>();
        try {
            if(cookieList!=null&&cookieList.size()>0){
                String url = almConfig.getUrl();
                String username = almConfig.getUsername();
                String password = almConfig.getPassword();
                if(url!=null&&!"".equals(url)&&username!=null&&!"".equals(username)&&password!=null&&!"".equals(password)){
                    url=url+"/rest/domains/"+domainName+"/projects/"+projectName+"/customization/entities/"+entityName+"/lists";
                    List<MediaType> mediaTypes = new ArrayList<>();
                    mediaTypes.add(MediaType.APPLICATION_JSON);
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.put(HttpHeaders.COOKIE,cookieList);
                    httpHeaders.setAccept(mediaTypes);
                    HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
                    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
                    String entityFieldJsonStr=response.getBody();
                    JSONObject jsonObject = JSON.parseObject(entityFieldJsonStr);
                    JSONArray fields = (JSONArray)jsonObject.get("lists");
                    if(fields.size()>0){
                        for(int i=0;i<fields.size();i++){
                            String s = fields.get(i).toString();
                            EntityFieldListValue entityFieldListValue = JSON.parseObject(s, EntityFieldListValue.class);
                            map.put(entityFieldListValue.getId(),entityFieldListValue);
                        }
                    }

                }
            }
            return new Result(true, StatusCode.OK,"查询集合实体字段的列表成功",map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询集合实体字段的列表失败,reason: "+e.getMessage());
            throw new RuntimeException("查询集合实体字段的列表失败");
        }
    }


    @Override
    public Result findEntities(String domainName, String projectName, String entityName, AlmConfig almConfig,List<String> cookieList,List<String> filters) {
        List<Map> list = new ArrayList<>();
        try {
            if(cookieList!=null&&cookieList.size()>0) {
                String url = almConfig.getUrl();
                String username = almConfig.getUsername();
                String password = almConfig.getPassword();
                if (url != null && !"".equals(url) && username != null && !"".equals(username) && password != null && !"".equals(password)) {
                    if(filters!=null&&filters.size()>0){
                        String filterStr="&&fields=";
                        for (int i=0;i<filters.size();i++) {
                            if(i==filters.size()-1){
                                filterStr=filterStr+filters.get(i);

                            }else {
                                filterStr=filterStr+filters.get(i)+",";
                            }
                        }
                        url = url + "/rest/domains/" + domainName + "/projects/" + projectName + "/" + entityName + "?page-size=10000"+filterStr;

                    }else {
                        url = url + "/rest/domains/" + domainName + "/projects/" + projectName + "/" + entityName + "?page-size=10000&&fields=id";
                    }
                    List<MediaType> mediaTypes = new ArrayList<>();
                    mediaTypes.add(MediaType.APPLICATION_JSON);
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.put(HttpHeaders.COOKIE,cookieList);
                    httpHeaders.setAccept(mediaTypes);
                    HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
                    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
                    String entityJsonStr=response.getBody();
                    JSONObject jsonObject = JSON.parseObject(entityJsonStr);
                    JSONArray entities = (JSONArray)jsonObject.get("entities");
                    for(int i=0;i<entities.size();i++){
                        JSONObject entityJsonObject=(JSONObject)entities.get(i);
                        JSONArray entityFiledJsonArray = (JSONArray)entityJsonObject.get("Fields");
                        Map<String, Object> entityMap = new HashMap<>();
                        for(int j=0;j<entityFiledJsonArray.size();j++){
                            JSONObject entityField=(JSONObject)entityFiledJsonArray.get(j);
                            String name = entityField.getString("Name");
                            String values = entityField.getString("values");
                            JSONArray parseArray = JSON.parseArray(values);
                            if(parseArray.size()>0){
                                JSONObject value=(JSONObject)parseArray.get(0);
                                entityMap.put(name,value.getString("value"));
                            }
                        }

                        list.add(entityMap);
                    }
                }
            }
            return new Result(true,StatusCode.OK,"查询成功",list);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询alm实体失败");
        }
    }

    @Override
    public Result queryEntityByFilters(String domainName, String projectName, String entityName, AlmConfig almConfig, List<String> cookieList, String filter) {
        List<Map> list = new ArrayList<>();
        try {
            if(cookieList!=null&&cookieList.size()>0) {
                String url = almConfig.getUrl();
                url = url + "/rest/domains/" + domainName + "/projects/" + projectName + "/" + entityName + "?query={filter}";
                    List<MediaType> mediaTypes = new ArrayList<>();
                    mediaTypes.add(MediaType.APPLICATION_JSON);
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.put(HttpHeaders.COOKIE,cookieList);
                    httpHeaders.setAccept(mediaTypes);
                    HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
                    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class,filter);
                    String entityJsonStr=response.getBody();
                    JSONObject jsonObject = JSON.parseObject(entityJsonStr);
                    JSONArray entities = (JSONArray)jsonObject.get("entities");
                    for(int i=0;i<entities.size();i++){
                        JSONObject entityJsonObject=(JSONObject)entities.get(i);
                        JSONArray entityFiledJsonArray = (JSONArray)entityJsonObject.get("Fields");
                        Map<String, Object> entityMap = new HashMap<>();
                        for(int j=0;j<entityFiledJsonArray.size();j++){
                            JSONObject entityField=(JSONObject)entityFiledJsonArray.get(j);
                            String name = entityField.getString("Name");
                            String values = entityField.getString("values");
                            JSONArray parseArray = JSON.parseArray(values);
                            if(parseArray.size()>0){
                                JSONObject value=(JSONObject)parseArray.get(0);
                                entityMap.put(name,value.getString("value"));
                            }
                        }
                        list.add(entityMap);
                    }
                }

            return new Result(true,StatusCode.OK,"查询成功",list);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("过滤查询alm实体失败");
        }
    }

    @Override
    public Result createEntity(String entityXml, String url, List<String> cookieList) {
        try {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_XML);
                httpHeaders.put(HttpHeaders.COOKIE, cookieList);
                HttpEntity<Object> httpEntity = new HttpEntity<>(entityXml,httpHeaders);
                ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, httpEntity, String.class);
                String insertStatus = postForEntity.getStatusCode().toString();
                return new Result(true,StatusCode.OK,"实体导入成功",insertStatus);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("实体导入失败");
        }
    }


}
