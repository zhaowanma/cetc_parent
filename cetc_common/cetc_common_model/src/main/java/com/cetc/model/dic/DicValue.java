package com.cetc.model.dic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel(description = "字典数据实体类")
public class DicValue implements Serializable {
    private static final long serialVersionUID = -6721362042182537528L;
    @ApiModelProperty(value = "字典数据的id")
    private Long id;

    @ApiModelProperty(value = "字典标签")
    private String key;

    @ApiModelProperty(value = "字典键值")
    private String value;

    @ApiModelProperty(value = "排序号")
    private Long order;

    @ApiModelProperty(value = "系统内置标识")
    private Boolean sys;

    @ApiModelProperty(value = "最近修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "状态标识")
    private Boolean status;

    @ApiModelProperty(value = "字典id")
    private Long dicId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Boolean getSys() {
        return sys;
    }

    public void setSys(Boolean sys) {
        this.sys = sys;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getDicId() {
        return dicId;
    }

    public void setDicId(Long dicId) {
        this.dicId = dicId;
    }
}
