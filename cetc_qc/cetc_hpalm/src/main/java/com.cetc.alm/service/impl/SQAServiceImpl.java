package com.cetc.alm.service.impl;
import com.cetc.alm.dao.AlmConfigDao;
import com.cetc.alm.dao.CodeDao;
import com.cetc.alm.service.AuthencateService;
import com.cetc.alm.service.EntityService;
import com.cetc.alm.service.ProjectService;
import com.cetc.alm.service.SQAService;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.dic.DicValue;
import com.cetc.model.hpalm.AlmConfig;
import com.cetc.model.hpalm.EntityField;
import com.cetc.model.project.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SQAServiceImpl implements SQAService {

    private Logger logger= LoggerFactory.getLogger(SQAServiceImpl.class);

    @Autowired
    private EntityService entityService;

    @Autowired
    private CodeDao codeDao;

    @Autowired
    private AlmConfigDao almConfigDao;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AuthencateService authencateService;

    @Override
    public Result findSQA(long codeId,int year) {
        List<Map> sqas = null;
        List<String> cookieList =null;
        AlmConfig almConfig=almConfigDao.findAlmConfig();
        try {
            cookieList = authencateService.login(almConfig);
            Code code = codeDao.findOne(codeId);
            String codeValue = code.getValue();
            String almDomainName = "产品SQA检查项目";
            String almProjectName = "产品SQA检查项目"+year;
            Result defectFields = entityService.findEntityFields(almDomainName, almProjectName, "defect", almConfig, cookieList);
            String cpdhFieldName = "";
            if (defectFields.isFlag()) {
                List<EntityField> entityList = (List<EntityField>) defectFields.getData();
                for (EntityField entityField : entityList) {
                    if ("产品代号".equals(entityField.getLabel())) {
                        cpdhFieldName = entityField.getName();
                        break;
                    }
                }
            }
            String filters = "{" + cpdhFieldName + "[\""+codeValue+"\"]}" ;
            Result result = entityService.queryEntityByFilters(almDomainName, almProjectName, "defects", almConfig, cookieList, filters);
            if (result.isFlag()) {
                sqas = (List<Map>) result.getData();
            }
            return new Result(true, StatusCode.OK, "ok", sqas);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询SQA列表失败****************"+e.getMessage()+"**********************");
            return new Result(false, StatusCode.ERROR, "ok", sqas);
        }finally {
            if(cookieList!=null){
                authencateService.logout(almConfig,cookieList);
            }

        }
    }

    @Override
    public Result findSQATableField(List<DicValue> dicValues,int year) {
        List<String> cookieList =null;
        AlmConfig almConfig=almConfigDao.findAlmConfig();
        try {
            String almDomainName = "产品SQA检查项目";
            String almProjectName = "产品SQA检查项目"+year;
            cookieList = authencateService.login(almConfig);
            Result defectFields = entityService.findEntityFields(almDomainName, almProjectName, "defect", almConfig, cookieList);
            List<String> sqaDicValues = new ArrayList<>();
            for (DicValue dicValue: dicValues) {
                sqaDicValues.add(dicValue.getValue());
            }
            if(defectFields.isFlag()){
                List<EntityField> entityList = (List<EntityField>) defectFields.getData();
                for (int i=0;i<entityList.size();i++) {
                    if(!sqaDicValues.contains(entityList.get(i).getLabel())){
                        entityList.remove(i);
                        i--;
                    }
                }
            }
            return defectFields;
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"ok",null);
        }finally {
            if(cookieList!=null){
                authencateService.logout(almConfig,cookieList);

            }
        }
    }


   public Result getSqaINFO(List<DicValue> dicValues,long codeId){
       try {
           Result projects = projectService.findProjectsByDomainName("产品SQA检查项目");
           List<String> almProjects=new ArrayList<>();
           if(projects.isFlag()){
               almProjects=(List<String>)projects.getData();
           }
           int startYear=2015;
           int endYear= Calendar.getInstance().get(Calendar.YEAR);
           int realYear=endYear;
           Map<String, Object> sqaInfo = new HashMap<>();
           for (int i=endYear ;i>=startYear;i--) {
               if(almProjects.contains("产品SQA检查项目"+i)){
                   Result sqa = findSQA(codeId, i);
                   if(sqa.isFlag()&&sqa.getData()!=null){
                       List sqaData = (List) sqa.getData();
                       if ((sqaData.size()>0)){
                           realYear=i;
                           sqaInfo.put("tableData",sqaData);
                           Result sqaFields = findSQATableField(dicValues, realYear);
                           if(sqaFields.isFlag()){
                               sqaInfo.put("tableColumnList",sqaFields.getData());
                           }
                           break;
                       }
                   }
               }
           }
        return new Result(true,StatusCode.OK,"ok",sqaInfo);
       }catch (Exception e){
           e.printStackTrace();
           return new Result(false,StatusCode.ERROR,"error");
       }
   }

}
