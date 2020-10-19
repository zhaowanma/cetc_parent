package com.cetc.alm.dao;


import com.cetc.model.project.DocumentCheck;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface DocumentCheckDao {

    DocumentCheck findById(long id);

}
