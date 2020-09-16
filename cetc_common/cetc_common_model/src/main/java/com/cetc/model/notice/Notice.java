package com.cetc.model.notice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(description = "通知实体类")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1483182744358060670L;

    @ApiModelProperty(value = "通知的id")
    private Long id;
    @ApiModelProperty(value = "通知的标题")
    private String title;
    @ApiModelProperty(value = "通知的内容")
    private String comment;
    @ApiModelProperty(value = "通知的接收用户名")
    private String checker;
    @ApiModelProperty(value = "通知的来源")
    private String creater;
    @ApiModelProperty(value = "通知的创建时间")
    private Date createTime;

    public Notice() {
    }

    public Notice(String title, String comment, String checker, String creater, Date createTime) {
        this.title = title;
        this.comment = comment;
        this.checker = checker;
        this.creater = creater;
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
