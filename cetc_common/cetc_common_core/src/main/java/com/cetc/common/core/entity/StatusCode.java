package com.cetc.common.core.entity;

/**
 * 状态码实体类
 */
public class StatusCode {

    public static final int OK=20000;//成功
    public static final int ERROR =20001;//失败
    public static final int LOGINERROR =20002;//用户名或密码错误
    public static final int USERNAMEUNEXIST =20003;//用户名不存在
    public static final int USERNAMEUNABLE =20004;//用户未激活
    public static final int REMOTEERROR =20005;//远程调用失败
    public static final int OVERTIME = 40001; //登录超时
    public static final int MD5EXIST = 50000; // 文件已存在
    public static final int MD5UNEXIST = 50001; // 文件不存在
}
