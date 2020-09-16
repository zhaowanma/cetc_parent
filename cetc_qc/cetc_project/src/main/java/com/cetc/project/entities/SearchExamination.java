package com.cetc.project.entities;

import com.cetc.common.core.entity.PageEntity;

import java.io.Serializable;
import java.util.Date;

public class SearchExamination extends PageEntity implements Serializable {

    private String project;
    private Date start;
    private Date end;
}
