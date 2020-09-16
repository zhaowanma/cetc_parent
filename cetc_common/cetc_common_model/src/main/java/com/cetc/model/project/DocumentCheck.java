package com.cetc.model.project;


import java.io.Serializable;
import java.util.Date;

public class DocumentCheck implements Serializable {

    private Long id;
    private Long parentId;
    private String checkLevel;           //c令号类型、p项目类型
    private String fileName;            //文件名称
    private String version;             //版本
    private String designer;            //设计者
    private String checker;             //审查者
    private String docType;             //文档类型，保存字典的label
    private String company;             //被考核单位，保存字典的label
    private String checkGrade;          //审查阶段，保存字典的label
    private String sysConfItem;         //系统配置项名称，保存字典的label
    private Integer pageSize;           //文档页码
    private Integer matterCount;        //问题总数
    private String kingdom;             //所属领域
    private Long kingdomId;             //所属领域
    private String code;                //所属令号
    private Long codeId;                //所属令号

    private String annoFlag;           //文档注解的id

    private String project;             //所属项目
    private Long projectId;             //所属项目
    private String remark;              //
    private Date createDate;            //
    private String createBy;            //
    private Date updateDate;            //
    private String updateBy;            //


    public String getAnnoFlag() {
        return annoFlag;
    }

    public void setAnnoFlag(String annoFlag) {
        this.annoFlag = annoFlag;
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

    public String getCheckLevel() {
        return checkLevel;
    }

    public void setCheckLevel(String checkLevel) {
        this.checkLevel = checkLevel;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getMatterCount() {
        return matterCount;
    }

    public void setMatterCount(Integer matterCount) {
        this.matterCount = matterCount;
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}

