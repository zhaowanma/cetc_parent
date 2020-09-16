package com.cetc.project.task;

import com.cetc.model.project.Project;
import com.cetc.project.mapper.ProjectDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GenerateProjectMonthData {
    Logger log = LoggerFactory.getLogger(GenerateProjectMonthData.class);

    @Autowired
    ProjectDao projectDao;

    // 每天凌晨1点实行一次
    @Scheduled(cron = "*/10 * * * * *")
    public void generate(){
        try {
            Project project = new Project();
            project.setStatus(true);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
