package com.cetc.alm.service;

import com.cetc.common.core.entity.Result;

public interface AnalysicService {
    public Result findProjectDataCount(long id,Boolean refresh);

    public Result testCaseCount(long id,Boolean refresh);

    public Result defectCount(long id,Boolean refresh);

    public Result testCaseTypeCount(long id,Boolean refresh);

}
