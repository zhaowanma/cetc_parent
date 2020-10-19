package com.cetc.alm.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.hpalm.AlmConfig;
import com.cetc.model.hpalm.AlmSiteConfig;

public interface AlmConfigService {

    public Result saveAlmConfig(AlmConfig almConfig);

    public Result findAlmConfig();

    public Result saveAlmSiteConfig(AlmSiteConfig almSiteConfig);

    public Result findAlmSiteConfig();
}
