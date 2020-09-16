package com.cetc.project.mapper;

import com.cetc.model.project.CodeTest;
import com.cetc.project.entities.SearchCodeTest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CodeTestDao {

    void addCodeTest(CodeTest codeTest);
    void deleteOne(Long id);
    void deleteCodeTest(SearchCodeTest searchcodeTest);
    void updateCodeTest(CodeTest codeTest);
    List<CodeTest> searchCodeTest(SearchCodeTest searchcodeTest);

}
