package com.cetc.project.mapper;

import com.cetc.model.project.Kingdom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface KingdomDao {

    void addKingdom(Kingdom kingdom);//新增
    void update(Kingdom kingdom);// 更新
    List<Kingdom> findAll();//  查询所有
    Kingdom findByName(@Param("name") String name);// 根据名称查询
    void deleteById(Long id);//  删除一个
    Kingdom findOne(@Param("id") Long id);// 查询

}
