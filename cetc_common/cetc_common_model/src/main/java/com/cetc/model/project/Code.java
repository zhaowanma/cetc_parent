package com.cetc.model.project;

import java.io.Serializable;
import java.util.Date;

public class Code implements Serializable {

    private Long id;
    private String name;		// 令号名称
    private String value;		// 令号
    private String kingdom;		// 领域
    private Long parentId;      //领域Id
    private String scope;		// 域
    private String leader;		// 总体负责人
    private String header;		// 组长
    private Integer num;		    // 序号
    private String remarks;	    // 备注
    private String createBy;	// 创建者
    private Date createDate;	// 创建日期
    private String updateBy;	// 更新者
    private Date updateDate;	// 更新日期
    private Boolean isApply;    //是否已申请
    private Boolean status;      //状态
    private Boolean isCompensate;  //是否补偿数据
    private Boolean isYh;      //是否要号
    private Boolean isZy ;     //是否在研
    private Date assignTime;   //任命时间
    private String softMan;     //软件负责人
    private String signalMan;   //电讯负责人
    private String qualityer;   //质量师
    private String projectMan;  //项目主管

    public Boolean getYh() {
        return isYh;
    }

    public void setYh(Boolean yh) {
        isYh = yh;
    }

    public Boolean getZy() {
        return isZy;
    }

    public void setZy(Boolean zy) {
        isZy = zy;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Boolean getCompensate() {
        return isCompensate;
    }

    public void setCompensate(Boolean compensate) {
        isCompensate = compensate;
    }

    public Boolean getApply() {
        return isApply;
    }

    public void setApply(Boolean apply) {
        isApply = apply;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getSoftMan() {
        return softMan;
    }

    public void setSoftMan(String softMan) {
        this.softMan = softMan;
    }

    public String getSignalMan() {
        return signalMan;
    }

    public void setSignalMan(String signalMan) {
        this.signalMan = signalMan;
    }

    public String getQualityer() {
        return qualityer;
    }

    public void setQualityer(String qualityer) {
        this.qualityer = qualityer;
    }

    public String getProjectMan() {
        return projectMan;
    }

    public void setProjectMan(String projectMan) {
        this.projectMan = projectMan;
    }
}
