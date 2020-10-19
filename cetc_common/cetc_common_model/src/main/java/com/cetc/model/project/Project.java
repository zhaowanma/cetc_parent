package com.cetc.model.project;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Project implements Serializable {
    private Long id;
    private Long parentId;  //父ID，对应令号的id
    private String name;    //项目名称
    private String kingdom; //所属领域
    private Long kingdomId; //所属领域
    private String code;    //所属令号
    private String testGrade;  // 测试级别
    private String testType;// 测试类型
    private String testLeader;//   测试负责人
    private String leader;    //总体负责人
    private String num;//项目序号
    private Boolean isApply;//  申请状态
    private Boolean status;//   项目状态
    private String almDomainName;//
    private String almProjectName;//
    private String createBy;//
    private Date createDate;//
    private String updateBy;//
    private Date updateDate;//
    private String softer;  //软件负责人
    private String codeSize; //代码行数
    private List<String> joins;//参与人员
    private Boolean isCompensate;  //是否补偿数据
    private Boolean isYh;      //是否要号
    private Boolean isZy ;     //是否在研
    private Date assignTime;   //任命时间
    private String remark;

    public Boolean getCompensate() {
        return isCompensate;
    }

    public Boolean getYh() {
        return isYh;
    }

    public Boolean getZy() {
        return isZy;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setCompensate(Boolean compensate) {
        isCompensate = compensate;
    }

    public void setYh(Boolean yh) {
        isYh = yh;
    }

    public void setZy(Boolean zy) {
        isZy = zy;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTestGrade() {
        return testGrade;
    }

    public void setTestGrade(String testGrade) {
        this.testGrade = testGrade;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getTestLeader() {
        return testLeader;
    }

    public void setTestLeader(String testLeader) {
        this.testLeader = testLeader;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Boolean getApply() {
        return isApply;
    }

    public void setApply(Boolean apply) {
        isApply = apply;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAlmDomainName() {
        return almDomainName;
    }

    public void setAlmDomainName(String almDomainName) {
        this.almDomainName = almDomainName;
    }

    public String getAlmProjectName() {
        return almProjectName;
    }

    public void setAlmProjectName(String almProjectName) {
        this.almProjectName = almProjectName;
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

    public String getSofter() {
        return softer;
    }

    public void setSofter(String softer) {
        this.softer = softer;
    }

    public String getCodeSize() {
        return codeSize;
    }

    public void setCodeSize(String codeSize) {
        this.codeSize = codeSize;
    }

    public List<String> getJoins() {
        return joins;
    }

    public void setJoins(List<String> joins) {
        this.joins = joins;
    }



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
