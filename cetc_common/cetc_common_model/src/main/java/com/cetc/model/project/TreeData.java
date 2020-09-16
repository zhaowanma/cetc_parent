package com.cetc.model.project;

import java.io.Serializable;
import java.util.List;

public class TreeData implements Serializable {

    private Long id;
    private String name;   //名称
    private String label;  //需要展示的名称

    private Boolean operate;//是否可操作


    public Boolean getOperate() {
        return operate;
    }

    public void setOperate(Boolean operate) {
        this.operate = operate;
    }

    private List<TreeData> children;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeData> getChildren() {
        return children;
    }

    public void setChildren(List<TreeData> children) {
        this.children = children;
    }
}
