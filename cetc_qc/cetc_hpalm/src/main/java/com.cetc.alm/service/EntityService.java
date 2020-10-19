package com.cetc.alm.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.hpalm.AlmConfig;
import com.cetc.model.hpalm.EntityField;

import java.util.List;

public interface EntityService {
    public Result findEntityFields(String domainName, String projectName, String entityName, AlmConfig almConfig,List<String> cookieList);

    public Result queryEntityListValues(String domainName, String projectName, String entityName, AlmConfig almConfig,List<String> cookieList);

    public Result findEntities(String domainName, String projectName, String entityName, AlmConfig almConfig,List<String> cookieList,List<String> filters);

    public Result queryEntityByFilters(String domainName, String projectName, String entityName, AlmConfig almConfig,List<String> cookieList,String filter );

    public Result createEntity(String entityXml,String url,List<String> cookieList );


}
