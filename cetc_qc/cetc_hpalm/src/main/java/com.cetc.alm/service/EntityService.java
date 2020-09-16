package com.cetc.alm.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.hpalm.AlmConfig;
import com.cetc.model.hpalm.EntityField;

import java.util.List;

public interface EntityService {
    public Result findEntityFields(String domainName, String projectName, String entityName, AlmConfig almConfig,List<String> cookieList);

    public Result findEntityFieldsJsonStr(String domainName, String projectName, String entityName, AlmConfig almConfig,List<String> cookieList);

    public Result findEntities(String domainName, String projectName, String entityName, AlmConfig almConfig,List<String> cookieList,List<String> filters);

   // public Result findPageEntities(String domainName, String projectName, String entityName,int pageSize ,AlmConfig almConfig,List<String> cookieList,List<String> filters);

    public Result findEntitiesJsonStr(String domainName, String projectName, String entityName, AlmConfig almConfig,List<String> cookieList);
}
