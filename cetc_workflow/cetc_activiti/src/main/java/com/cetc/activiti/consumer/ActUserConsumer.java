package com.cetc.activiti.consumer;
import com.cetc.model.activiti.constants.ActUserQueue;
import com.cetc.model.admin.SysUser;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RabbitListener(queues = ActUserQueue.ACTUSER_QUEUE)
public class ActUserConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ActUserConsumer.class);

    @Autowired
    private IdentityService identityService;
    @RabbitHandler
    @Transactional
    public void actUserHandler(SysUser sysUser){
        try {
            if(!sysUser.getType()){
                if(sysUser.getDelflag()){
                    identityService.deleteUser(sysUser.getUsername());
                }else if(sysUser.getId()>0) {
                    User user = identityService.createUserQuery().userId(sysUser.getUsername()).singleResult();
                    if(user!=null){
                        user.setFirstName(sysUser.getFullname());
                        identityService.saveUser(user);
                    }else {
                        User newuser = identityService.newUser(sysUser.getUsername());
                        newuser.setFirstName(sysUser.getFullname());
                        identityService.saveUser(newuser);
                    }

                }else {
                    User user = identityService.newUser(sysUser.getUsername());
                    user.setFirstName(sysUser.getFullname());
                    identityService.saveUser(user);
                }
            }
        }catch (Exception e){
            logger.error("工作流用户更新失败 reason:"+e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("工作流用户更新失败");
        }
    }
}
