package com.cetc.project.entities;

import com.cetc.common.core.entity.PageEntity;

import java.io.Serializable;
import java.util.Date;

public class SearchProject extends PageEntity implements Serializable {
    private String createBy;
    private Long parentId;
    private Long kingdomId;
    private String name;
    private String num;
    private String almProjectName;
    private String testGrade;
    private String testLeader;
    private String join;
    private int applicant;
    private Integer year;
    private Boolean status;
    private Date start;
    private Date end;
    private Boolean isZy;


    public String getAlmProjectName() {
        return almProjectName;
    }

    public void setAlmProjectName(String almProjectName) {
        this.almProjectName = almProjectName;
    }

    public Boolean getZy() {
        return isZy;
    }

    public void setZy(Boolean zy) {
        isZy = zy;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getKingdomId() {
        return kingdomId;
    }

    public void setKingdomId(Long kingdomId) {
        this.kingdomId = kingdomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestGrade() {
        return testGrade;
    }

    public void setTestGrade(String testGrade) {
        this.testGrade = testGrade;
    }

    public String getTestLeader() {
        return testLeader;
    }

    public void setTestLeader(String testLeader) {
        this.testLeader = testLeader;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public int getApplicant() {
        return applicant;
    }

    public void setApplicant(int applicant) {
        this.applicant = applicant;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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
