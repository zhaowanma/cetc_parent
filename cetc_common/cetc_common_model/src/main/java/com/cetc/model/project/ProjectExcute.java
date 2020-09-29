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

    private Long monthCommitId;

    private Integer examples;
    private boolean passFlag;
    private Date createDate;
    private String createBy;
    private Date updateDate;
    private String updateBy;
    private String passer;
    private Date passDate;
    private int useTime;

//以下为执行问题的字段
    private String testQus;

    private String testType;

    private String zerenbumen;

    private Long designQusNum;

    private Long codeQusNum;

    private Long docQusNum;

    private Long bianmaQusNum;

    private Long qitaQusNum;

    private Long guanjianQusNum;

    private Long zhongyaoQusNum;

    private Long yibanQusNum;

    private Long jianyigaijinQusNum;

    private Double daimaguimo;

    private Long testQusNum;

    private String rjkfman;

    public String getTestQus() {
        return testQus;
    }

    public void setTestQus(String testQus) {
        this.testQus = testQus;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getZerenbumen() {
        return zerenbumen;
    }

    public void setZerenbumen(String zerenbumen) {
        this.zerenbumen = zerenbumen;
    }

    public Long getDesignQusNum() {
        return designQusNum;
    }

    public void setDesignQusNum(Long designQusNum) {
        this.designQusNum = designQusNum;
    }

    public Long getCodeQusNum() {
        return codeQusNum;
    }

    public void setCodeQusNum(Long codeQusNum) {
        this.codeQusNum = codeQusNum;
    }

    public Long getDocQusNum() {
        return docQusNum;
    }

    public void setDocQusNum(Long docQusNum) {
        this.docQusNum = docQusNum;
    }

    public Long getBianmaQusNum() {
        return bianmaQusNum;
    }

    public void setBianmaQusNum(Long bianmaQusNum) {
        this.bianmaQusNum = bianmaQusNum;
    }

    public Long getQitaQusNum() {
        return qitaQusNum;
    }

    public void setQitaQusNum(Long qitaQusNum) {
        this.qitaQusNum = qitaQusNum;
    }

    public Long getGuanjianQusNum() {
        return guanjianQusNum;
    }

    public void setGuanjianQusNum(Long guanjianQusNum) {
        this.guanjianQusNum = guanjianQusNum;
    }

    public Long getZhongyaoQusNum() {
        return zhongyaoQusNum;
    }

    public void setZhongyaoQusNum(Long zhongyaoQusNum) {
        this.zhongyaoQusNum = zhongyaoQusNum;
    }

    public Long getYibanQusNum() {
        return yibanQusNum;
    }

    public void setYibanQusNum(Long yibanQusNum) {
        this.yibanQusNum = yibanQusNum;
    }

    public Long getJianyigaijinQusNum() {
        return jianyigaijinQusNum;
    }

    public void setJianyigaijinQusNum(Long jianyigaijinQusNum) {
        this.jianyigaijinQusNum = jianyigaijinQusNum;
    }

    public Double getDaimaguimo() {
        return daimaguimo;
    }

    public void setDaimaguimo(Double daimaguimo) {
        this.daimaguimo = daimaguimo;
    }

    public Long getTestQusNum() {
        return testQusNum;
    }

    public void setTestQusNum(Long testQusNum) {
        this.testQusNum = testQusNum;
    }

    public String getRjkfman() {
        return rjkfman;
    }

    public void setRjkfman(String rjkfman) {
        this.rjkfman = rjkfman;
    }

    public Long getMonthCommitId() {
        return monthCommitId;
    }

    public void setMonthCommitId(Long monthCommitId) {
        this.monthCommitId = monthCommitId;
    }

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
