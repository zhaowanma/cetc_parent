package com.cetc.alm.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.hpalm.AlmConfig;

public interface AlmConfigService {

    public Result saveAlmConfig(AlmConfig almConfig);

    public Result findAlmConfig();
}
