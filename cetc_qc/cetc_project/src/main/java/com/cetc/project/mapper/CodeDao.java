package com.cetc.project.mapper;

import com.cetc.model.project.Code;
import com.cetc.project.entities.SearchCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CodeDao {

    void addCode(Code code);
    //历史数据补偿使用，请勿删除
    void importTemplateCode(Code code);

    Code findOne(Long id);

    void updateCode(Code code);

    List<Code> findAll();

    //仅供页面加载时分页使用
    List<Code> fuzzyQueryList(Map map);


    List<Code> queryList(SearchCode searchCode);

    void deleteOne(Long id);

    Code findByCodeValue(String value);

    List<Code> findByKingdom(Long parentId);

    void setLeader(@Param("id") Long id,@Param("username") String username);

    List<Code> findCodeByField(Code code);
}
