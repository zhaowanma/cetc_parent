package com.cetc.dic.dao;

import com.cetc.model.dic.Dictionary;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DicDao {
     void insert(Dictionary dictionary);

     List<Dictionary> queryAll();

     Dictionary queryById(Long id);

     Dictionary queryByDicFields(Map fields);

     void updateDic(Dictionary dictionary);

     List<Dictionary> fuzzyQuery(Map params);

     void delete(Long id);

}
