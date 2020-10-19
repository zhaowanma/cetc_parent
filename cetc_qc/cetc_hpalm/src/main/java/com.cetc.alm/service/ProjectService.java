package com.cetc.alm.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.hpalm.AlmConfig;
import com.cetc.model.hpalm.AlmExecLog;

import java.util.List;
import java.util.Map;

public interface ProjectService {

    Result findProjects(AlmConfig almConfig, String domainName, List<String> cookieList);

    Result findProjectsByDomainName(String domainName);

    void createAlmProjects(Map map);

    Result findAlmProjectCreateLog(long id);
}


