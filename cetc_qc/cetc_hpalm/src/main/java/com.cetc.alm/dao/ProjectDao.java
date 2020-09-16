package com.cetc.alm.dao;

import com.cetc.model.project.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface ProjectDao {
    Project findOne(@Param("id") Long id);

}
