package com.cetc.alm.dao;

import com.cetc.model.hpalm.AlmConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AlmConfigDao {

    void saveAlmConfig(AlmConfig almConfig);

    void updateAlmConfig(AlmConfig almConfig);

    AlmConfig findAlmConfig();
}
