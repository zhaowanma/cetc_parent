package com.cetc.model.project;

import java.io.Serializable;
import java.util.Date;

public class ProjectMonthCommit implements Serializable {

    private Long id;

    private String commitMan;

    private Date commitTime;

    private String commitTimeStr;

    private Long projectId;

    private String remarks;

    private Date createTime;

    private String createTimeStr;

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCommitTimeStr() {
        return commitTimeStr;
    }

    public void setCommitTimeStr(String commitTimeStr) {
        this.commitTimeStr = commitTimeStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommitMan() {
        return commitMan;
    }

    public void setCommitMan(String commitMan) {
        this.commitMan = commitMan;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
