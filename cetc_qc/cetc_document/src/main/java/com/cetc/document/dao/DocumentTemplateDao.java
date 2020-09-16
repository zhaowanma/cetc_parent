package com.cetc.document.dao;

import com.cetc.model.document.DocumentTemplate;
import com.cetc.model.project.Code;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DocumentTemplateDao {

    void save(DocumentTemplate documentTemplate);

    void delete(long id);

    DocumentTemplate findById(long id);

    void update(DocumentTemplate documentTemplate);

    List<DocumentTemplate> fuzzyQueryList(Map map);

}
