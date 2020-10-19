package com.cetc.model.hpalm;

import java.io.Serializable;
import java.util.List;

public class EntityFieldListValue implements Serializable {
    private String Name;

    private int Id;

    private String LogicalName;

    private List<EntityFieldListValueItem> Items;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLogicalName() {
        return LogicalName;
    }

    public void setLogicalName(String logicalName) {
        LogicalName = logicalName;
    }

    public List<EntityFieldListValueItem> getItems() {
        return Items;
    }

    public void setItems(List<EntityFieldListValueItem> items) {
        Items = items;
    }
}
