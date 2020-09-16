package com.cetc.admin.entity;

import com.cetc.admin.utils.infreface.DataTree;

import java.util.List;

public class TreeMenu implements DataTree<TreeMenu> {

    private int id;

    private int parentId;

    private String label;

    private List<TreeMenu> childList;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public List<TreeMenu> getChildList() {
        return childList;
    }

    @Override
    public void setChildList(List<TreeMenu> childList) {
        this.childList = childList;
    }
}
