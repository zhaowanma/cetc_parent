package com.cetc.project.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.TreeData;

import java.util.List;

public interface ProjectTreeService {

    Result findTreeData();

    Result findMyTreeData(List<TreeData>  projectList);
}
