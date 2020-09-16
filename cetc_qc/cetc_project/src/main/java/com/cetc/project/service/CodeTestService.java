package com.cetc.project.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.CodeTest;
import com.cetc.project.entities.SearchCodeTest;

public interface CodeTestService {
    Result addCodeTest(CodeTest codeTest);
    Result delCodeTest(Long id);
    Result pageList(SearchCodeTest searchCodeTest);
    Result updateCodeTest(CodeTest codeTest);
}
