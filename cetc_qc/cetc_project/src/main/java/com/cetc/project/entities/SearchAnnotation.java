package com.cetc.project.entities;

import com.cetc.common.core.entity.PageEntity;

import java.io.Serializable;
import java.util.Date;

public class SearchAnnotation extends PageEntity implements Serializable {
    private Long parentId;
    private String annoLevel;
    private String annoType;
    private Date start;
    private Date end;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAnnoLevel() {
        return annoLevel;
    }

    public void setAnnoLevel(String annoLevel) {
        this.annoLevel = annoLevel;
    }

    public String getAnnoType() {
        return annoType;
    }

    public void setAnnoType(String annoType) {
        this.annoType = annoType;
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
