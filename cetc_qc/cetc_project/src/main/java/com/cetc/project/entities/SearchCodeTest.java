package com.cetc.project.entities;

import com.cetc.common.core.entity.PageEntity;

import java.io.Serializable;
import java.util.Date;

public class SearchCodeTest extends PageEntity implements Serializable {
     private Long parentId;
     private String testName;
     private Date start;
     private Date end;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
