package com.cetc.project.task;

import com.cetc.project.mapper.AnnotationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class DelAnno {
    Logger log = LoggerFactory.getLogger(DelAnno.class);
    @Autowired
    private AnnotationDao annotationDao;
    // 每天凌晨1点实行一次
    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteAnnotation(){
        log.info("定时任务删除多余的文档审查问题");
        annotationDao.delAnnoParentNull();
    }

    // 每天凌晨1点实行一次
    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteFiles(){
        log.info("定时任务删除多余的文档");
        annotationDao.delAnnoParentNull();
    }




}
