package com.cetc.project.mapper;

import com.cetc.model.project.Code;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CodeVisitableDao {

    void insertCodeUsername(@Param("codeId") long codeId, @Param("username") String username);

    void deleteByUsernameAndCodeId(@Param("codeId") long codeId, @Param("username") String username);

    void deleteByCodeId(@Param("codeId") long codeId);

    Code findCodeByCodeIdAndUsername(@Param("codeId") long codeId, @Param("username") String username);

    List<String> findUsernamesByCodeId(@Param("codeId") long codeId);
}
