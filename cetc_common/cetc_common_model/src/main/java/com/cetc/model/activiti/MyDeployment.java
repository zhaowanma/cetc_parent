package com.cetc.model.activiti;

import java.io.Serializable;
import java.util.Date;

public class MyDeployment implements Serializable {
    private String id;
    private String name;
    private Date DeploymentTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeploymentTime() {
        return DeploymentTime;
    }

    public void setDeploymentTime(Date deploymentTime) {
        DeploymentTime = deploymentTime;
    }
}
