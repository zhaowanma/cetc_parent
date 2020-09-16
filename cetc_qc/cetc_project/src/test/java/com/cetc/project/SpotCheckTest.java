package com.cetc.project;

import com.cetc.model.project.Project;
import com.cetc.project.entities.SearchProject;
import com.cetc.project.mapper.ProjectDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.crypto.dsig.SignatureMethod;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpotCheckTest {

   @Autowired
   private ProjectDao projectDao;

    @Test
    public void add(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
//        calendar.set(Calendar.MONTH,1);  //设置月份
//        calendar.set(Calendar.DAY_OF_MONTH,1); //设置日期
//        calendar.set(Calendar.HOUR_OF_DAY,0);//设置小时
//        calendar.set(Calendar.MINUTE,0);//设置分钟
//        calendar.set(Calendar.SECOND,0);//设置秒
//        Date start = calendar.getTime();
//       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(sdf.format(start));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.setFirstDayOfWeek(Calendar.MONDAY); //设置每周的第一天
        calendar.setTimeInMillis(System.currentTimeMillis());//设置当前日期
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.add(Calendar.WEEK_OF_MONTH,-1);
        for(int i=0;i<=7;i++){
            System.out.println(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_WEEK,1);

        }



    }

}
