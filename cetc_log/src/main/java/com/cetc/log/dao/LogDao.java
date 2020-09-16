package com.cetc.log.dao;


import com.cetc.model.log.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LogDao {

    void save(Log log);

    List<Log> findAll();

    List<Log> queryData(Map<String, Object> params);

}
