package com.cetc.model.document;

import java.io.Serializable;
import java.util.Date;

public class Document implements Serializable {
    private Long id;
    private Long parentFolderId;       //所属文件夹
    private String docName;             //文档名称
    private String docType;             //文档类型
    private String docSrc;              //文档路径
    private Integer totalSize;          //文件大小
    private String uploader;            //上传者
    private String checker;             //审批人
    private String secretGrade;         //秘密等级
    private String remarks;              //备注
    private String dept;                //
    private boolean checkedConfirm;     //审查结果
    private Date createDate;            //
    private String createBy;            //
    private Date updateDate;            //
    private String updateBy;            //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(Long parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocSrc() {
        return docSrc;
    }

    public void setDocSrc(String docSrc) {
        this.docSrc = docSrc;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getSecretGrade() {
        return secretGrade;
    }

    public void setSecretGrade(String secretGrade) {
        this.secretGrade = secretGrade;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public boolean isCheckedConfirm() {
        return checkedConfirm;
    }

    public void setCheckedConfirm(boolean checkedConfirm) {
        this.checkedConfirm = checkedConfirm;
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
}
