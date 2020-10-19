package com.cetc.model.hpalm;


import java.io.Serializable;
import java.util.Date;

public class AlmExecLog implements Serializable {

    private Long id;

    private String content;

    private Date execTime;

    private String execTimeStr;

    private String businessType;

    private Long businessId;

    private String remarks;

    private Boolean status;

    private String icon;

    private String color;

    public String getExecTimeStr() {
        return execTimeStr;
    }

    public void setExecTimeStr(String execTimeStr) {
        this.execTimeStr = execTimeStr;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public AlmExecLog() {
        super();
       }

    public AlmExecLog(String content, Date execTime, String businessType, Long businessId, String remarks, Boolean status, String icon, String color) {
        this.content = content;
        this.execTime = execTime;
        this.businessType = businessType;
        this.businessId = businessId;
        this.remarks = remarks;
        this.status = status;
        this.icon = icon;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getExecTime() {
        return execTime;
    }

    public void setExecTime(Date execTime) {
        this.execTime = execTime;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
