package com.cetc.alm.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.hpalm.AlmConfig;

import java.util.List;

public interface DomainService {
     Result findDomains(AlmConfig almConfig,List<String> cookieList);

     Result findAlmDomains();

}
