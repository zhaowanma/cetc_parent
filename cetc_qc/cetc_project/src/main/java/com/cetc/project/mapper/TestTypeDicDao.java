package com.cetc.project.mapper;

import com.cetc.project.entities.TestTypeDic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TestTypeDicDao {

    void addptd(@Param("projectId") Long projectId,@Param("dicId") Long dicId);
    void delptd(@Param("projectId") Long projectId);
    List<TestTypeDic> findByProjectId(@Param("projectId") Long projectId);



}
