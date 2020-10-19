package com.cetc.alm.utils;
import com.cetc.model.hpalm.AlmSiteConfig;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteAdminUtils {

    private static final Logger logger= LoggerFactory.getLogger(SiteAdminUtils.class);

    private static Dispatch disp;


    public static synchronized Dispatch getDispatchInstance(AlmSiteConfig almSiteConfig){
        if(disp!=null){
             return disp;
        }else {
            return getDisp(almSiteConfig);
        }
    }

    public static Dispatch getDisp(AlmSiteConfig almSiteConfig){
        ComThread.InitSTA();
        ActiveXComponent com = new ActiveXComponent("SAClient.SAapi.9");
        Dispatch disp = com.getObject();
        String url=almSiteConfig.getUrl();
        String username = almSiteConfig.getUsername();
        String password = almSiteConfig.getPassword();
        try {
            Dispatch.call(disp,"Login",url,username,password);
            return disp;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("****************登录alm站点disp失败**************"+e.getMessage());
            throw new RuntimeException("登录alm站点disp失败，请检查是否有空闲license和配置是否正确");

        }
    }

}
