package com.cetc.project.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.Kingdom;
import java.util.Map;

public interface KingdomService {
    Result save(Kingdom kingdom);
    Result findAll();
    Result page(Map<String,Integer> map);
    Result findByName(String name);
    Result  delete(Long id);


}
