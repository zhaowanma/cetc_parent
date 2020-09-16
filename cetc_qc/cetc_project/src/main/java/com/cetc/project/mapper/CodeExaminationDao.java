package com.cetc.project.mapper;

import com.cetc.model.project.CodeExamination;
import com.cetc.project.entities.SearchExamination;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CodeExaminationDao {

    void add(CodeExamination examination);

    void deleteOne(Long id);

    List<CodeExamination> queryList(SearchExamination searchExamination);

    void update(CodeExamination examination);
}
