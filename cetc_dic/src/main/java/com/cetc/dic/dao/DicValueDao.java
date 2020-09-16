package com.cetc.dic.dao;

import com.cetc.model.dic.DicValue;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DicValueDao {

     List queryAll(@Param("dicId") Long dicId, @Param("params") Map params);

     void saveDicValue(DicValue dicValue);

     DicValue queryById(Long id);

     void updateById(DicValue dicValue);

     DicValue queryByKey(DicValue dicValue);

     void deleteById(Long id);

     void deleteByDicId(Long dicId);

     List queryAllDicValuesByDicType(Long dicId);
}
