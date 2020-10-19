package com.cetc.alm.dao;

import com.cetc.model.project.Code;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CodeDao {
    Code findOne(@Param("id") Long id);

}
