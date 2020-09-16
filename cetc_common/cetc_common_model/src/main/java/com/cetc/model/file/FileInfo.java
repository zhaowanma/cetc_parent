package com.cetc.model.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel(description ="文件信息实体类" )
public class FileInfo implements Serializable {
    private static final long serialVersionUID = 9117612271559751919L;
    @ApiModelProperty(value = "实体id")
    private Long id;
    @ApiModelProperty(value = "文件名")
    private String filename;
    @ApiModelProperty(value = "文件标识")
    private String identifier;
    @ApiModelProperty(value = "总大小")
    private Long totalSize;
    @ApiModelProperty(value = "文件类型")
    private String type;
    @ApiModelProperty(value = "文件存储地址")
    private String location;
    @ApiModelProperty(value = "文件上传时间")
    private Date uploadDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
