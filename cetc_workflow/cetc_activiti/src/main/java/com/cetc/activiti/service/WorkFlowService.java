package com.cetc.activiti.service;

import com.cetc.common.core.entity.PageResult;
import com.cetc.common.core.entity.Result;

public interface WorkFlowService {
    /**
     * 查询所有模型
     * @param pageResult
     * @return
     */
    Result findModelList(PageResult pageResult);

    /**
     * 删除模型
     * @param id
     * @return
     */
    Result delModel(String id);

    /**
     * 发布流程
     * @param id
     * @return
     */
    Result publishModel(String id);

    /**
     * 撤销发布流程
     * @param id
     * @return
     */
    Result revokePublish(String id);

    /**
     * 查询部署列表
     * @return
     */
    Result findDeploymentList(PageResult pageResult);

    /**
     * 删除部署对象
     * @param id
     * @return
     */
    Result deldeployment(String id);

    /**
     * 查询流程定义列表
     * @return
     */
    Result processDefinitionList(PageResult pageResult);

    /**
     * 查询流程图
     * @param definitionId
     * @return
     */
    Result viewImage(String definitionId);





}
