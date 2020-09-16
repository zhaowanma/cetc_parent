package com.cetc.project.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.Annotation;
import com.cetc.model.project.DocumentCheck;
import com.cetc.project.entities.SearchAnnotation;
import com.cetc.project.entities.SearchDocumentCheck;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface DocumentCheckService {
    Result add(Map<String, Object> map);
    Result update(DocumentCheck documentCheck);
    Result pageList(SearchDocumentCheck searchDocumentCheck);
    Result deleteOne(DocumentCheck documentCheck);
    Result analysisFile(String path);
    Result pageAnnoList(SearchAnnotation searchAnnotation);
    Result deleteAnno(Annotation annotation);
    Result getAnalysisStatus(String uuid);
    Result getAnnoByUuid(Map<String, String> map);
    Result saveFile(MultipartFile file);
    Result countOfDocAndAnnotationAndPageSize(SearchDocumentCheck searchDocumentCheck);
    Result countOfDocType(SearchDocumentCheck searchDocumentCheck);
    Result countOfAnnotationType(SearchDocumentCheck searchDocumentCheck);

}
