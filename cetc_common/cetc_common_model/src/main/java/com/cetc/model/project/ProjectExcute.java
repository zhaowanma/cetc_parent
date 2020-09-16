package com.cetc.model.project;

import java.io.Serializable;
import java.util.Date;

/**
 * 测试执行
 */
public class ProjectExcute implements Serializable {
    private Long id;
    private String name;
    private Long kingdomId;
    private String kingdom;
    private Long codeId;
    private String code;
    private Long projectId;
    private String project;
    private Date excuteDate;
    private String excuteLocal;
    private Integer excuters;
    private Integer examples;
    private boolean passFlag;
    private Date createDate;
    private String createBy;
    private Date updateDate;
    private String updateBy;
    private String passer;
    private Date passDate;
    private int useTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getKingdomId() {
        return kingdomId;
    }

    public void setKingdomId(Long kingdomId) {
        this.kingdomId = kingdomId;
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Date getExcuteDate() {
        return excuteDate;
    }

    public void setExcuteDate(Date excuteDate) {
        this.excuteDate = excuteDate;
    }

    public String getExcuteLocal() {
        return excuteLocal;
    }

    public void setExcuteLocal(String excuteLocal) {
        this.excuteLocal = excuteLocal;
    }

    public Integer getExcuters() {
        return excuters;
    }

    public void setExcuters(Integer excuters) {
        this.excuters = excuters;
    }

    public Integer getExamples() {
        return examples;
    }

    public void setExamples(Integer examples) {
        this.examples = examples;
    }

    public boolean isPassFlag() {
        return passFlag;
    }

    public void setPassFlag(boolean passFlag) {
        this.passFlag = passFlag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getPasser() {
        return passer;
    }

    public void setPasser(String passer) {
        this.passer = passer;
    }

    public Date getPassDate() {
        return passDate;
    }

    public void setPassDate(Date passDate) {
        this.passDate = passDate;
    }

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }
}
