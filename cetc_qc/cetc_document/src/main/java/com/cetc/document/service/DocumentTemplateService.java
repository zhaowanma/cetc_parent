package com.cetc.document.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.document.DocumentTemplate;

import java.io.IOException;
import java.util.Map;

public interface DocumentTemplateService {

     Result saveDocumentTemplate(DocumentTemplate documentTemplate) throws IOException;


     Result delete(long id);


     Result update(DocumentTemplate documentTemplate);


     Result fuzzyPageQueryList(Map map);


     Result findById(long id);

}
