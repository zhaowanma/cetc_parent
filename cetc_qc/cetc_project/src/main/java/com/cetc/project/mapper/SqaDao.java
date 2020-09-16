package com.cetc.project.mapper;


import com.cetc.model.project.SQA;
import com.cetc.project.entities.SearchSQA;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SqaDao {
    int deleteByPrimaryKey(Long id);

    int insert(SQA record);

    int insertSelective(SQA record);

    SQA selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SQA record);

    int updateByPrimaryKey(SQA record);

    List<SQA> findByParent(Map<String, Object> params);

    Integer getMaxIndex();

    List<SQA> pageShow(SQA sqa);

    List<SQA> findByKingDomName(@Param("kingdomId") Long kingdomId);

    List<SQA> findByCode(@Param("codeId") Long codeId);

    //tianby  delete
    void deleteSQA(SearchSQA searchSQA);
}