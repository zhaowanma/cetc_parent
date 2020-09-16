package com.cetc.dic.dao;

import com.cetc.model.dic.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ParamDao {
     List<Param> findAll(Map params);

     void insert (Param param);

     Param queryById(Long id);

     void updateById(Param param);

     void delete(Long id);

     Param queryByKey(String key);

}
