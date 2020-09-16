package com.cetc.model.dic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(description = "系统参数实体类")
public class Param implements Serializable {
    private static final long serialVersionUID = -2550329548598027832L;

    @ApiModelProperty(value = "系统参数id")
    private Long id;
    @ApiModelProperty(value = "参数名称")
    private String paramName;
    @ApiModelProperty(value = "参数键名")
    private String paramKey;
    @ApiModelProperty(value = "参数键值")
    private String paramValue;
    @ApiModelProperty(value = "系统参数标识")
    private Boolean paramSys;
    @ApiModelProperty(value = "备注")
    private String remarks;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Boolean getParamSys() {
        return paramSys;
    }

    public void setParamSys(Boolean paramSys) {
        this.paramSys = paramSys;
    }
}
