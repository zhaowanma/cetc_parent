package com.cetc.project.entities;

import java.io.Serializable;

public class TestTypeDic implements Serializable {
    private Long projectId;
    private Long dicId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getDicId() {
        return dicId;
    }

    public void setDicId(Long dicId) {
        this.dicId = dicId;
    }
}
