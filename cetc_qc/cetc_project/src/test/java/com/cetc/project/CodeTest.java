package com.cetc.project;

import com.cetc.project.mapper.CodeDao;
import com.cetc.project.service.CodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CodeTest {
    @Autowired
    private CodeService codeService;
    @Autowired
    private CodeDao codeDao;
    @Test
    public void test1(){
        String num = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String year = sdf.format(new Date());
        System.out.println(year);
    }

    @Test
    public void test2() {

    }
}
