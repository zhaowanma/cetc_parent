package com.cetc.alm.dao;

import com.cetc.model.hpalm.AlmExecLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AlmExecLogDao {

    public void save(AlmExecLog almExecLog);

    public List<AlmExecLog> findByBusinsessType(@Param("businessType") String businessType,@Param("businessId") Long businessId);

}
