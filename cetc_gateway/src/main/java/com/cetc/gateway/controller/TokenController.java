package com.cetc.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.gateway.feign.DicClient;
import com.cetc.gateway.feign.Oauth2Client;
import com.cetc.gateway.feign.UserClient;
import com.cetc.gateway.utils.RedisUtil;
import com.cetc.model.admin.LoginSysUser;
import com.cetc.model.admin.SysUser;
import com.cetc.model.dic.Param;
import com.cetc.model.log.LogAnnotation;
import com.cetc.model.oauth.SystemClientInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.*;

import javax.naming.Context;
import javax.naming.ldap.InitialLdapContext;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
@Api(tags = "token控制层类")
public class TokenController {

    private final static Logger logger= LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private Oauth2Client oauth2Client;

    @Autowired
    private UserClient userClient;

    @Autowired
    private DicClient dicClient;
    
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/sys/login")
    @ApiOperation("系统登录接口")
    @LogAnnotation(module = "系统登录")
    public Result login(@RequestBody SysUser sysUser){
        try{
            LoginSysUser loginSysUser = userClient.findByUsername(sysUser.getUsername());
            if(loginSysUser==null){
                return new Result(false,StatusCode.USERNAMEUNEXIST,"用户名不存在");
            }else if (!loginSysUser.isEnabled()){
                return new Result(false,StatusCode.USERNAMEUNABLE,"用户未激活，请联系系统管理员");
            }
            //如果登录用户不是内置用户，需要走所内AD鉴权接口
            if(!loginSysUser.getType()){
                Result result = dicClient.queryByKey("cetc_ad_url");
                if(!result.isFlag()){
                   logger.error("*****系统参数cetc_ad_url调用失败,服务降级*******");
                   throw new RuntimeException("*****系统参数cetc_ad_url调用失败,服务降级*******");
                }
                String server=null;
                  if(result.getData()!=null){
                      String s = JSON.toJSONString(result.getData());
                      Param param = JSON.parseObject(s, Param.class);
                      server=param.getParamValue();
                      Hashtable<Object, Object> env = new Hashtable<>();
                      env.put(Context.SECURITY_PRINCIPAL,sysUser.getUsername());
                      env.put(Context.SECURITY_CREDENTIALS,sysUser.getPassword());
                      env.put(Context.PROVIDER_URL,server);
                      env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
                      env.put(Context.SECURITY_AUTHENTICATION,"simple");
                      InitialLdapContext initialLdapContext=null;
                      try {
                         // initialLdapContext= new InitialLdapContext(env, null);
                          logger.info("14AD认证通过了");
                      }catch (Exception e){
                          logger.error("*****************"+"用户"+sysUser.getUsername()+"AD认证失败"+"****************");
                          logger.error("*****************"+e.getMessage()+"****************");
                          return new Result(false,StatusCode.ERROR,"AD认证失败");
                      }finally {
                          if(initialLdapContext!=null){
                                 try {
                                     initialLdapContext.close();
                                 }catch (Exception e){
                                     logger.error("*****************关闭initialLdapContext失败"+e.getMessage()+"****************");
                                     e.printStackTrace();
                                 }
                          }
                      }
                      sysUser.setPassword(sysUser.getUsername());
                  }else {
                      logger.error("*****系统参数cetc_ad_url为空*******");
                      throw new RuntimeException("*****系统参数cetc_ad_url为空*******");
                  }

            }else {
                String password = loginSysUser.getPassword();
                int loginTimes=3;
                if(redisUtil.hashGet("cetc_login_times",sysUser.getUsername())!=null){
                    loginTimes = (int)redisUtil.hashGet("cetc_login_times",sysUser.getUsername());
                }
                if(!encoder.matches(sysUser.getPassword(),password)){
                    if(loginTimes>0){
                        redisUtil.hashSet("cetc_login_times",sysUser.getUsername(),loginTimes-1);
                        return new Result(false,StatusCode.ERROR,"密码错误,剩余"+(loginTimes-1)+"次机会");
                    }else {
                        return new Result(false,StatusCode.ERROR,"该账户已经被锁定");
                    }
                }else {
                    if(loginTimes>0){
                        redisUtil.hashSet("cetc_login_times",sysUser.getUsername(),3);
                    }else {
                        return new Result(false,StatusCode.ERROR,"该账户已经被锁定");
                    }
                }
            }
            //认证通过，释放令牌
                Map<String, String> parameters = new HashMap<>();
                parameters.put(OAuth2Utils.GRANT_TYPE, "password");
                parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
                parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
                parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
                // 为了支持多类型登录，这里在username后拼装上登录类型
                parameters.put("username", sysUser.getUsername());
                parameters.put("password", sysUser.getPassword());
                Map<String, Object> tokens = oauth2Client.postAccessToken(parameters);
                if(tokens!=null){
                    return new Result(true, StatusCode.OK,"登录成功",tokens);
                }
                return new Result(false,StatusCode.LOGINERROR,"登录失败，用户名或密码错误");

        }catch (Exception e){
            e.printStackTrace();
            logger.error("登录错误"+e.getMessage());
            return new Result(false,StatusCode.REMOTEERROR,"登录失败");
        }
    }

    @GetMapping("/sys/refreshToken/{refreshToken}")
    @ApiOperation("系统令牌刷新接口")
    public Result refreshToken(@PathVariable String refreshToken){
           Map<String, String> parameters = new HashMap<>();
           parameters.put(OAuth2Utils.GRANT_TYPE, "refresh_token");
           parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
           parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
           parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
            // 为了支持多类型登录，这里在username后拼装上登录类型
           parameters.put("refresh_token", refreshToken);

        if(oauth2Client.postAccessToken(parameters)!=null){
            return new Result(true, StatusCode.OK,"更新token成功",oauth2Client.postAccessToken(parameters));
        }
        return new Result(false,StatusCode.LOGINERROR,"更新token失败");
    }
}
