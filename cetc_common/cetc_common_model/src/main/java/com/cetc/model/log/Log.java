package com.cetc.model.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;


@ApiModel(description = "日志实体类")
public class Log implements Serializable {
    private static final long serialVersionUID = 2208923493336453090L;

    @ApiModelProperty(value = "日志id")
    private Long id;
    /** 用户名 */
    @ApiModelProperty(value = "操作用户名")
    private String username;
    /**真实姓名*/
    @ApiModelProperty(value = "操作用户姓名")
    private String fullname;
    /** 模块 */
    @ApiModelProperty(value = "操作内容")
    private String module;
    /** 参数值 */
    @ApiModelProperty(value = "携带参数")
    private String params;
    /** 错误信息 */
    @ApiModelProperty(value = "错误信息")
    private String remark;
    /** 请求地址 */
    @ApiModelProperty(value = "请求地址")
    private String address;
    /** 用户的ip */
    @ApiModelProperty(value = "用户登录ip")
    private String remoteIp;
    /** 请求方法 */
    @ApiModelProperty(value = "请求方法")
    private String method;
   /** 服务id*/
   @ApiModelProperty(value = "访问服务")
    private String serviceClient;
     /** 执行时间*/
     @ApiModelProperty(value = "执行时间")
    private int execTime;
    /** 是否执行成功 */
    @ApiModelProperty(value = "是否执行成功")
    private Boolean flag;
    /**访问时间*/
    @ApiModelProperty(value = "日志生成时间")
    private Date createTime;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getServiceClient() {
        return serviceClient;
    }

    public void setServiceClient(String serviceClient) {
        this.serviceClient = serviceClient;
    }

    public int getExecTime() {
        return execTime;
    }

    public void setExecTime(int execTime) {
        this.execTime = execTime;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
