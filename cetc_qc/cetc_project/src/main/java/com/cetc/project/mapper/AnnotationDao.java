package com.cetc.project.mapper;

import com.cetc.model.project.Annotation;
import com.cetc.project.entities.SearchAnnotation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnnotationDao {

    void addAnnotation(Annotation annotation);
    void deleteOne(Long id);
    List<Annotation> findByUuid(String uuid);
    void setParent(@Param("uuid") String uuid, @Param("parentId") Long parentId,@Param("annoLevel") String annoLevel);
    void deleteAnnotation(SearchAnnotation searchAnnotation);
    void delAnnoParentNull();
    List<Annotation> queryList(SearchAnnotation searchAnnotation);

}
