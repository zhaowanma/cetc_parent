package com.cetc.test;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TestApplication {

    @Test
    public void test(){
        ComThread.InitSTA();
        ActiveXComponent com = new ActiveXComponent("SAClient.SAapi.9");
        Dispatch disp = com.getObject();
        String almUrl="http://192.168.0.123:8080/qcbin";
        Dispatch.call(disp,"Login",almUrl,"administrator","admin_123456");
        Dispatch.call(disp, "CreateDomain","haha","","",-1);
        Dispatch.call(disp, "Logout");
        System.out.println("完成");
    }

}
