package com.cetc.model.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description ="文件分片实体类" )
public class Chunk3 implements Serializable {


    private static final long serialVersionUID = -6969132784730596433L;
    @ApiModelProperty(value = "文件分片实体id")
    private Long id;
    /**
     * 当前文件块，从1开始
     */
    @ApiModelProperty(value = "当前块")
    private Integer chunkNumber;
    /**
     * 分块大小
     */
    @ApiModelProperty(value = "分块大小")
    private Long chunkSize;
    /**
     * 当前分块大小
     */
    @ApiModelProperty(value = "当前分块大小")
    private Long currentChunkSize;
    /**
     * 总大小
     */
    @ApiModelProperty(value = "总大小")
    private Long totalSize;
    /**
     * 文件标识
     */
    @ApiModelProperty(value = "文件标识")
    private String identifier;
    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String filename;
    /**
     * 相对路径
     */
    @ApiModelProperty(value = "文件相对路径")
    private String relativePath;
    /**
     * 总块数
     */
    @ApiModelProperty(value = "总块数")
    private Integer totalChunks;
    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getChunkNumber() {
        return chunkNumber;
    }

    public void setChunkNumber(Integer chunkNumber) {
        this.chunkNumber = chunkNumber;
    }

    public Long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public Long getCurrentChunkSize() {
        return currentChunkSize;
    }

    public void setCurrentChunkSize(Long currentChunkSize) {
        this.currentChunkSize = currentChunkSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public Integer getTotalChunks() {
        return totalChunks;
    }

    public void setTotalChunks(Integer totalChunks) {
        this.totalChunks = totalChunks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
