package com.cetc.alm.dao;
import com.cetc.model.hpalm.AlmSiteConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AlmSiteConfigDao {

    void saveAlmSiteConfig(AlmSiteConfig almSiteConfig);

    void updateAlmSiteConfig(AlmSiteConfig almSiteConfig);

    AlmSiteConfig findAlmSiteConfig();
}
