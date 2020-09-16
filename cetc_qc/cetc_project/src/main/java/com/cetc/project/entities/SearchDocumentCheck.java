package com.cetc.project.entities;

import com.cetc.common.core.entity.PageEntity;

import java.io.Serializable;
import java.util.Date;

public class SearchDocumentCheck extends PageEntity implements Serializable {
    private Long parentId;       //父级id
    private String checkLevel;   //c令号类型、p项目类型
    private String docType;      //文档类型
    private String company;      //被考核单位，
    private String checkGrade;   //审查阶段
    private String sysConfItem;  //系统配置项名称，
    private String designer;     //设计者
    private String checker;      //审查者
    private Date start;          //开始时间
    private Date end;            //结束时间
    private Long kingdomId;
    private Long codeId;
    private Long projectId;
    private Integer year;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCheckLevel() {
        return checkLevel;
    }

    public void setCheckLevel(String checkLevel) {
        this.checkLevel = checkLevel;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCheckGrade() {
        return checkGrade;
    }

    public void setCheckGrade(String checkGrade) {
        this.checkGrade = checkGrade;
    }

    public String getSysConfItem() {
        return sysConfItem;
    }

    public void setSysConfItem(String sysConfItem) {
        this.sysConfItem = sysConfItem;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
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

    public Long getKingdomId() {
        return kingdomId;
    }

    public void setKingdomId(Long kingdomId) {
        this.kingdomId = kingdomId;
    }

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
