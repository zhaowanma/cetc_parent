package com.cetc.dic.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.dic.Param;

import java.util.Map;

public interface ParamService {

     Result findPageParams(Map map);

     Result saveParam(Param param);

     Result queryById(Long id);

     Result updateById(Param param);

     Result deleteById(Long id);

     Result queryByKey(String key);

}
