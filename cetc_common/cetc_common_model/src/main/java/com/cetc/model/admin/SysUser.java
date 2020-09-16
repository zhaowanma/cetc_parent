package com.cetc.model.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel(description = "用户实体类")
public class SysUser implements Serializable {


    private static final long serialVersionUID = 8364249792419815724L;
    @ApiModelProperty(value = "用户的id")
    private int id;
    @ApiModelProperty(value = "用户的帐户")
    private String username;
    @ApiModelProperty(value = "用户的密码")
    private String password;
    @ApiModelProperty(value = "用户的真实姓名")
    private String fullname;
    @ApiModelProperty(value = "用户的联系方式")
    private String phone;
    @ApiModelProperty(value = "用户的性别")
    private Boolean sex;
    @ApiModelProperty(value = "用户状态")
    private Boolean status;
/*
* 账户类型
* */
    @ApiModelProperty(value = "用户类型")
    private Boolean type;
    /**
     * 状态
     */
    @ApiModelProperty(value = "启用标记")
    private Boolean enabled;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "最近密码修改时间")
    private Date passwordCheckTime;
    @ApiModelProperty(value = "删除标记")
    private Boolean delflag;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getPasswordCheckTime() {
        return passwordCheckTime;
    }

    public void setPasswordCheckTime(Date passwordCheckTime) {
        this.passwordCheckTime = passwordCheckTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDelflag() {
        return delflag;
    }

    public void setDelflag(Boolean delflag) {
        this.delflag = delflag;
    }
}
