package com.cetc.model.project;

import java.io.Serializable;
import java.util.Date;

public class FpgaStandard implements Serializable {
    private Long id;
    private Long parentId;
    private String project;
    private Integer totalProblems;    //问题总数
    private Integer numRuleViolations;   //违反规则数量
    private Integer numViolations;       //违反建议数量
    private Integer scaleCodeReview;     //代码审查规模
    private Integer totalTime;  //总耗时
    private Integer typeTest;   //检测类型
    private String createBy;	// 创建者
    private Date createDate;	// 创建日期
    private String updateBy;	// 更新者
    private Date updateDate;	// 更新日期
    private String description; //描述
    private String code;
    private Long codeId;
    private String kingdom;
    private Long kingdomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Integer getTotalProblems() {
        return totalProblems;
    }

    public void setTotalProblems(Integer totalProblems) {
        this.totalProblems = totalProblems;
    }

    public Integer getNumRuleViolations() {
        return numRuleViolations;
    }

    public void setNumRuleViolations(Integer numRuleViolations) {
        this.numRuleViolations = numRuleViolations;
    }

    public Integer getNumViolations() {
        return numViolations;
    }

    public void setNumViolations(Integer numViolations) {
        this.numViolations = numViolations;
    }

    public Integer getScaleCodeReview() {
        return scaleCodeReview;
    }

    public void setScaleCodeReview(Integer scaleCodeReview) {
        this.scaleCodeReview = scaleCodeReview;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getTypeTest() {
        return typeTest;
    }

    public void setTypeTest(Integer typeTest) {
        this.typeTest = typeTest;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public Long getKingdomId() {
        return kingdomId;
    }

    public void setKingdomId(Long kingdomId) {
        this.kingdomId = kingdomId;
    }

    @Override
    public String toString() {
        return "FpgaStandard{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", project='" + project + '\'' +
                ", totalProblems=" + totalProblems +
                ", numRuleViolations=" + numRuleViolations +
                ", numViolations=" + numViolations +
                ", scaleCodeReview=" + scaleCodeReview +
                ", totalTime=" + totalTime +
                ", typeTest=" + typeTest +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate=" + updateDate +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", codeId=" + codeId +
                ", kingdom='" + kingdom + '\'' +
                ", kingdomId=" + kingdomId +
                '}';
    }
}
