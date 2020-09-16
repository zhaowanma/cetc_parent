package com.cetc.alm.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.hpalm.AlmConfig;

import java.util.List;

public interface ProjectService {

    Result findProjects(AlmConfig almConfig, String domainName, List<String> cookieList);

    Result findProjectsByDomainName(String domainName);
}


