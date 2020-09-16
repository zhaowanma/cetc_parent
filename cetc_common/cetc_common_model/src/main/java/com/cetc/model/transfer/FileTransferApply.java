package com.cetc.model.transfer;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(description = "文件交换申请单实体类")
public class FileTransferApply implements Serializable{

    @ApiModelProperty(value = "申请单的id")
    private Long id;
    @ApiModelProperty(value = "申请用户的id")
    private Long userId;
    @ApiModelProperty(value = "申请人工号")
    private String applyWorkNum;
    @ApiModelProperty(value = "表单主题")
    private String title;
    @ApiModelProperty(value = "申请人姓名")
    private String applyName;
    @ApiModelProperty(value = "申请人部门")
    private String dept;
    @ApiModelProperty(value = "申请人联系方式")
    private String phone;
    @ApiModelProperty(value = "表单编号")
    private String applyTableNum;
    @ApiModelProperty(value = "文件数据目的系统")
    private String transferDes;
    @ApiModelProperty(value = "文件最高密级")
    private String fileSecurity;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "保密承诺")
    private Boolean checkedConfirm;

    public Boolean getCheckedConfirm() {
        return checkedConfirm;
    }

    public void setCheckedConfirm(Boolean checkedConfirm) {
        this.checkedConfirm = checkedConfirm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getApplyWorkNum() {
        return applyWorkNum;
    }

    public void setApplyWorkNum(String applyWorkNum) {
        this.applyWorkNum = applyWorkNum;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApplyTableNum() {
        return applyTableNum;
    }

    public void setApplyTableNum(String applyTableNum) {
        this.applyTableNum = applyTableNum;
    }

    public String getTransferDes() {
        return transferDes;
    }

    public void setTransferDes(String transferDes) {
        this.transferDes = transferDes;
    }

    public String getFileSecurity() {
        return fileSecurity;
    }

    public void setFileSecurity(String fileSecurity) {
        this.fileSecurity = fileSecurity;
    }

    private List<Long> fileIds;

    public List<Long> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<Long> fileIds) {
        this.fileIds = fileIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }




}
