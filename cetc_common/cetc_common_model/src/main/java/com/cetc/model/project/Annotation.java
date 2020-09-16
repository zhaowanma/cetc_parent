package com.cetc.model.project;

import java.io.Serializable;
import java.util.Date;

public class Annotation implements Serializable {
    private Long id;     //批注Id
    private Long parentId;//父级ID  文档审查ID
    private String type;   //批注类型
    private String annoLevel;//批注名称
    private String author;  // 作者
    private String text;    //批注内容
    private String uuid;
    private Date createDate;

    public Annotation() {
    }
    public Annotation(String type, String author, String text, String uuid, Date createDate) {
        this.type = type;
        this.author = author;
        this.text = text;
        this.uuid = uuid;
        this.createDate = createDate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnnoLevel() {
        return annoLevel;
    }

    public void setAnnoLevel(String annoLevel) {
        this.annoLevel = annoLevel;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

