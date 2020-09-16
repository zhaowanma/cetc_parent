package com.cetc.model.activiti;

import java.io.Serializable;
import java.util.Date;

public class MyProcessInstance implements Serializable {


    private static final long serialVersionUID = -6379544563723758914L;
    private String processInstanceId;

    private Date startTime;

    private Date endTime;

    private String businessKey;

    private String currentChecker;

    private String name;

    private String processDefinitionKey;

    public String getCurrentChecker() {
        return currentChecker;
    }

    public void setCurrentChecker(String currentChecker) {
        this.currentChecker = currentChecker;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }
}
