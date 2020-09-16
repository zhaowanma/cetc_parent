package com.cetc.log.autoconfigure;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.model.admin.LoginSysUser;
import com.cetc.model.log.Log;
import com.cetc.model.log.LogAnnotation;
import com.cetc.model.log.constants.LogQueue;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Aspect
public class LogAop {

    private static final Logger logger = LoggerFactory.getLogger(LogAop.class);
    @Autowired
    private AmqpTemplate amqpTemplate;

    //获取服务的名称
    @Value("${spring.application.name}")
    private String serviceClient;

    //增强处理
    @Around(value = "@annotation(com.cetc.model.log.LogAnnotation)")
    public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
        //代码执行开始时间
        long startTime = System.currentTimeMillis();
        //新建日志类
        Log log = new Log();
        //添加创建时间
        log.setCreateTime(new Date());
        //获取登录用户
        LoginSysUser loginSysUser = LoginUserUtil.getLoginSysUser();
        if(loginSysUser!=null){
            //为日志添加用户名
           log.setUsername(loginSysUser.getUsername());
            //为日志添加真名
           log.setFullname(loginSysUser.getFullname());
        }
        //获取http请求
        ServletRequestAttributes requestAttributes =(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取访问地址
        log.setAddress(request.getRequestURI().toString());
        //获取远程地址
        log.setRemoteIp(request.getRemoteAddr());
        //获取请求类型
        log.setMethod(request.getMethod());
        log.setServiceClient(serviceClient);
        //获取切点
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
        //获取被访问接口的日志注释信息
        log.setModule(logAnnotation.module());
        if(logAnnotation.recordParam()){
            String[] parameters = methodSignature.getParameterNames();
            if(parameters!=null&&parameters.length>0){
                Object[] args = joinPoint.getArgs();
                for (int i = 0; i < parameters.length; i++) {
                     Object value = args[i];
                    if(log.getUsername()==null||"".equals(log.getUsername())){
                        if("sysUser".equals(parameters[i])){
                            JSONObject sysUserObject = JSON.parseObject(JSON.toJSONString(value));
                            log.setUsername((String)sysUserObject.get("username"));
                        }
                    }
                }
                try{
                    //获取浏览器信息
                    String  browserDetails  =   request.getHeader("User-Agent");
                    log.setParams(browserDetails);
                } catch (Exception e){
                    logger.error("记录参数失败：{}",e.getMessage());
                }
            }
        }
        try {
            //执行业务代码
            Object object = joinPoint.proceed();
            //为日志添加执行结果
            log.setFlag(Boolean.TRUE);
            //为日志添加执行时间
            log.setExecTime((int)(System.currentTimeMillis()-startTime));
            return object;
        } catch (Exception e) {
            log.setFlag(Boolean.FALSE);
            log.setRemark(e.getMessage());
            log.setExecTime((int)(System.currentTimeMillis()-startTime));
            throw e;
        } finally {
            //发送日志信息到消息总线中
                CompletableFuture.runAsync(()->{
                 try {
                     amqpTemplate.convertAndSend(LogQueue.LOG_QUEUE, log);
                     logger.info("发送日志到队列：{}", log);
                 }catch (Exception e2){
                     e2.printStackTrace();
                     logger.error("发送日志到队列失败：{}",e2.getMessage());
                 }
            });
        }

    }

}
