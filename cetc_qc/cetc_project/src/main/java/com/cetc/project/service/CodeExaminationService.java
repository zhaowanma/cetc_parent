package com.cetc.project.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.CodeExamination;
import com.cetc.project.entities.SearchExamination;

/**
 * 代码抽查服务类
 */
public interface CodeExaminationService {
    Result addSpotCheck(CodeExamination codeExamination);
    Result deleteOne(Long id);
    Result pageList(SearchExamination searchExamination);
    Result updateSpotCheck(CodeExamination codeExamination);
}
