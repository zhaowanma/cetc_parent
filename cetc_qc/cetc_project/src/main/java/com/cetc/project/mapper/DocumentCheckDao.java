package com.cetc.project.mapper;


import com.cetc.model.project.DocumentCheck;
import com.cetc.project.entities.SearchDocumentCheck;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DocumentCheckDao {

    void addDocument(DocumentCheck documentCheck);
    void deleteOne(Long id);
    void deleteDocumentCheck(SearchDocumentCheck searchDocumentCheck);
    void updataDocument(DocumentCheck documentCheck);
    List<DocumentCheck> queryList(SearchDocumentCheck searchDocumentCheck);


}
