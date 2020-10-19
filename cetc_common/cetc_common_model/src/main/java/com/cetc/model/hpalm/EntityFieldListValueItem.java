package com.cetc.model.hpalm;

import java.io.Serializable;

public class EntityFieldListValueItem implements Serializable {

    private String logicalName;

    private String value;

    public String getLogicalName() {
        return logicalName;
    }

    public void setLogicalName(String logicalName) {
        this.logicalName = logicalName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
