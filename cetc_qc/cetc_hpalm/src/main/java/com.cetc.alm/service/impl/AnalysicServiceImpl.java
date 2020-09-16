package com.cetc.alm.service.impl;

import com.alibaba.fastjson.JSON;
import com.cetc.alm.config.utils.CookieUtil;
import com.cetc.alm.config.utils.RedisUtil;
import com.cetc.alm.dao.AlmConfigDao;
import com.cetc.alm.dao.ProjectDao;
import com.cetc.alm.service.AnalysicService;
import com.cetc.alm.service.EntityService;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.hpalm.EntityField;
import com.cetc.model.project.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalysicServiceImpl implements AnalysicService {

    private static final Logger logger= LoggerFactory.getLogger(AnalysicServiceImpl.class);

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private EntityService entityService;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AlmConfigDao almConfigDao;
    @Override
    public Result findProjectDataCount(long id,Boolean refresh) {
        try {
            Project project = projectDao.findOne(id);
            if(project!=null&&project.getAlmDomainName()!=null&&project.getAlmProjectName()!=null){
                if(refresh){
                    redisUtil.delete(project.getAlmDomainName() + project.getAlmProjectName() + "1");
                }
                String json = (String)redisUtil.get(project.getAlmDomainName() + project.getAlmProjectName() + "1");
                if(json!=null&&!json.equals("")){
                    List list = (List)JSON.parseArray(json);
                    return new Result(true, StatusCode.OK,"ok",list);
                }
                List<String> cookieList = cookieUtil.getCookieList();
                Result reqFieldResult = entityService.findEntityFields(project.getAlmDomainName(), project.getAlmProjectName(), "requirement", almConfigDao.findAlmConfig(), cookieList);
                //需求属性
                String reqField="";
                List<String> filter = new ArrayList<>();
                List<String> filter2 = new ArrayList<>();
                filter2.add("id");
                List<String> filter3 = new ArrayList<>();
                filter3.add("id");
                int softAttrsNum=0;
                int testAttrsNum=0;
                int testCaseNum=0;
                int testStepsNum=0;
                int defectsNum=0;
                Map<String, Object> data = new HashMap<>();
                List<Object> dataList = new ArrayList<>();
                if(reqFieldResult.isFlag()){
                    List<EntityField> reqEntityFields=(List)reqFieldResult.getData();
                    for (EntityField entityField: reqEntityFields) {
                        if("需求属性".equals(entityField.getLabel())){
                            reqField=entityField.getName();
                            filter.add(reqField);
                        }
                    }
                }
                Result reqResult = entityService.findEntities(project.getAlmDomainName(), project.getAlmProjectName(), "requirements", almConfigDao.findAlmConfig(), cookieList, filter);
                Result defectsResult = entityService.findEntities(project.getAlmDomainName(), project.getAlmProjectName(), "defects", almConfigDao.findAlmConfig(), cookieList, filter2);
                Result designResult = entityService.findEntities(project.getAlmDomainName(), project.getAlmProjectName(), "design-steps", almConfigDao.findAlmConfig(), cookieList, filter3);

                if(reqResult.isFlag()){
                    List<Map> requirements = (List)reqResult.getData();
                    for (Map map: requirements) {
                          if(map.containsKey(reqField)){
                              String r = (String)map.get(reqField);
                              if("软件需求".equals(r)){
                                  softAttrsNum+=1;
                              }
                              if("测试需求".equals(r)){
                                  testAttrsNum+=1;
                              }
                             if("测试用例".equals(r)){
                                 testCaseNum+=1;
                             }
                          }
                    }
                }
                if(defectsResult.isFlag()){
                    List<Map> defects = (List)defectsResult.getData();
                    if(defects!=null&&defects.size()>0){
                        defectsNum=defects.size();
                    }
                }
                if(designResult.isFlag()){
                    List<Map> designs = (List)designResult.getData();
                    if(designs!=null&&designs.size()>0){
                        testStepsNum=designs.size();
                    }
                }
                data.put("name","数量");
              data.put("softreq",softAttrsNum);
              data.put("testreq",testAttrsNum);
              data.put("testcase",testCaseNum);
              data.put("teststep",testStepsNum);
              data.put("defect",defectsNum);
              dataList.add(data);
                String s = JSON.toJSONString(dataList);
                redisUtil.setIfAbsentNoTime(project.getAlmDomainName()+project.getAlmProjectName()+"1",s);
                return new Result(true, StatusCode.OK,"ok",dataList);

            }else {
                return new Result(false, StatusCode.OK,"本地项目不可用（未关联到alm项目）");
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error("统计项目数据表错误"+e.getMessage());
            return new Result(false, StatusCode.OK,"失败");

        }

    }

    @Override
    public Result testCaseCount(long id,Boolean refresh) {
      try {
          Project project = projectDao.findOne(id);
          if(project!=null&&project.getAlmDomainName()!=null&&project.getAlmProjectName()!=null){
              if(refresh){
                  redisUtil.delete(project.getAlmDomainName() + project.getAlmProjectName() + "2");
              }
              String json = (String)redisUtil.get(project.getAlmDomainName() + project.getAlmProjectName() + "2");
              if(json!=null&&!json.equals("")){
                  List list = (List)JSON.parseArray(json);
                  return new Result(true, StatusCode.OK,"ok",list);
              }
              List<String> cookieList = cookieUtil.getCookieList();
              Result reqFieldResult = entityService.findEntityFields(project.getAlmDomainName(), project.getAlmProjectName(), "requirement", almConfigDao.findAlmConfig(), cookieList);
              //需求属性
              String reqField="";
              //覆盖状态
              String coverStatus="";
              int testCaseFailed=0;
              int testCasePassed=0;
              int testCaseNoRun=0;
              int testCaseNoConvere=0;
              int testCaseBlocaked=0;
              int testCaseNoComplated=0;
              int testCaseNA=0;
              List<String> filter = new ArrayList<>();
              if(reqFieldResult.isFlag()){
                  List<EntityField> reqEntityFields=(List)reqFieldResult.getData();
                  for (EntityField entityField: reqEntityFields) {
                      if("需求属性".equals(entityField.getLabel())){
                          reqField=entityField.getName();
                          filter.add(reqField);
                      }
                      if("直接覆盖状态".equals(entityField.getLabel())){
                          coverStatus=entityField.getName();
                          filter.add(coverStatus);
                      }
                  }
              }
              Result reqResult = entityService.findEntities(project.getAlmDomainName(), project.getAlmProjectName(), "requirements", almConfigDao.findAlmConfig(), cookieList, filter);
              if(reqResult.isFlag()){
                  List<Map> requirements = (List)reqResult.getData();
                  for (Map map: requirements) {
                      if(map.containsKey(reqField)){
                          String r = (String)map.get(reqField);
                          if("测试用例".equals(r)&&map.containsKey(coverStatus)){
                              String cv= (String)map.get(coverStatus);
                              if("Failed".equals(cv)){
                                  testCaseFailed+=1;
                              }
                              if("Passed".equals(cv)){
                                   testCasePassed+=1;
                              }
                              if("No Run".equals(cv)){
                                   testCaseNoRun+=1;
                               }
                              if("Not Covered".equals(cv)){
                                  testCaseNoConvere+=1;
                              }
                              if("Blocked".equals(cv)){
                                  testCaseBlocaked+=1;
                              }
                             if("Not Completed".equals(cv)){
                                 testCaseNoComplated+=1;
                             }
                             if("N/A".equals(cv)){
                                   testCaseNA+=1;
                             }
                          }
                      }
                  }
              }
              List<Map> dataList = new ArrayList<>();
              Map<Object, Object> map1 = new HashMap<>();
              Map<Object, Object> map2 = new HashMap<>();
              Map<Object, Object> map3 = new HashMap<>();
              Map<Object, Object> map4= new HashMap<>();
              Map<Object, Object> map5= new HashMap<>();
              Map<Object, Object> map6 = new HashMap<>();
              Map<Object, Object> map7= new HashMap<>();
              map1.put("name","Failed");
              map1.put("value",testCaseFailed);
              dataList.add(map1);
              map2.put("name","Passed");
              map2.put("value",testCasePassed);
              dataList.add(map2);
              map3.put("name","No Run");
              map3.put("value",testCaseNoRun);
              dataList.add(map3);
              map4.put("name","Not Covered");
              map4.put("value",testCaseNoConvere);
              dataList.add(map4);
              map5.put("name","Blocked");
              map5.put("value",testCaseBlocaked);
              dataList.add(map5);
              map6.put("name","Not Completed");
              map6.put("value",testCaseNoComplated);
              dataList.add(map6);
              map7.put("name","N/A");
              map7.put("value",testCaseNA);
              dataList.add(map7);
              String s = JSON.toJSONString(dataList);
              redisUtil.setIfAbsentNoTime(project.getAlmDomainName()+project.getAlmProjectName()+"2",s);
              return new Result(true, StatusCode.OK,"ok",dataList);
          }else {
              return new Result(false, StatusCode.OK,"本地项目不可用（未关联到alm项目）");
          }
      }catch (Exception e){
          e.printStackTrace();
          logger.error("统计测试用例状态错误"+e.getMessage());
          return new Result(false, StatusCode.OK,"失败");
      }

    }

    @Override
    public Result defectCount(long id,Boolean refresh) {
        try {
            Project project = projectDao.findOne(id);
            if(project!=null&&project.getAlmDomainName()!=null&&project.getAlmProjectName()!=null){
                if(refresh){
                    redisUtil.delete(project.getAlmDomainName() + project.getAlmProjectName() + "3");
                }
                String json = (String)redisUtil.get(project.getAlmDomainName() + project.getAlmProjectName() + "3");
                if(json!=null&&!json.equals("")){
                    List list = (List)JSON.parseArray(json);
                    return new Result(true, StatusCode.OK,"ok",list);
                }
                int defect1=0;
                int defect2=0;
                int defect3=0;
                int defect4=0;
                //严重程度
                String servityField="";
                List<String> cookieList = cookieUtil.getCookieList();
                Result defectFieldResult = entityService.findEntityFields(project.getAlmDomainName(), project.getAlmProjectName(), "defect", almConfigDao.findAlmConfig(), cookieList);
                List<String> filter = new ArrayList<>();
                if(defectFieldResult.isFlag()){
                    List<EntityField> defectEntityFields=(List)defectFieldResult.getData();
                    for (EntityField entityField: defectEntityFields) {
                        if("严重程度".equals(entityField.getLabel())){
                            servityField=entityField.getName();
                            filter.add(servityField);
                        }
                    }
                }
                Result defectsResult = entityService.findEntities(project.getAlmDomainName(), project.getAlmProjectName(), "defects", almConfigDao.findAlmConfig(), cookieList, filter);
                if(defectsResult.isFlag()){
                    List<Map> defects = (List)defectsResult.getData();
                    for (Map defect: defects) {
                        if(defect.containsKey(servityField)){
                            String ds = (String)defect.get(servityField);
                            if("关键缺陷".equals(ds)||"第1级问题".equals(ds)||"致命缺陷".equals(ds)) {
                                defect1+=1;
                            }
                            if("严重缺陷".equals(ds)||"第2级问题".equals(ds)||"重要缺陷".equals(ds)) {
                                defect2+=1;
                            }
                            if("一般缺陷".equals(ds)||"第3级问题".equals(ds)||"第4级问题".equals(ds)) {
                                defect3+=1;
                            }
                            if("建议改进".equals(ds)||"第5级问题".equals(ds)) {
                                defect4+=1;
                            }
                        }
                    }

                }
                      List<Map> dataList = new ArrayList<>();
                      Map<Object, Object> map1 = new HashMap<>();
                      Map<Object, Object> map2 = new HashMap<>();
                      Map<Object, Object> map3 = new HashMap<>();
                      Map<Object, Object> map4= new HashMap<>();
                      map1.put("name","关键缺陷");
                      map1.put("value",defect1);
                      dataList.add(map1);
                      map2.put("name","严重缺陷");
                      map2.put("value",defect2);
                      dataList.add(map2);
                      map3.put("name","一般缺陷");
                      map3.put("value",defect3);
                      dataList.add(map3);
                      map4.put("name","建议改进");
                      map4.put("value",defect4);
                      dataList.add(map4);
                String s = JSON.toJSONString(dataList);
                redisUtil.setIfAbsentNoTime(project.getAlmDomainName()+project.getAlmProjectName()+"3",s);
                      return new Result(true, StatusCode.OK,"ok",dataList);

            }else {
                return new Result(false, StatusCode.OK,"本地项目不可用（未关联到alm项目）");
            }


        }catch (Exception e){
            e.printStackTrace();
            logger.error("统计缺陷错误"+e.getMessage());
            return new Result(false, StatusCode.OK,"失败");
        }
    }

    @Override
    public Result testCaseTypeCount(long id,Boolean refresh) {
        try {
            Project project = projectDao.findOne(id);
            if(project!=null&&project.getAlmDomainName()!=null&&project.getAlmProjectName()!=null){
                if(refresh){
                    redisUtil.delete(project.getAlmDomainName() + project.getAlmProjectName() + "4");
                }
                String json = (String)redisUtil.get(project.getAlmDomainName() + project.getAlmProjectName() + "4");
                if(json!=null&&!json.equals("")){
                    List list = (List)JSON.parseArray(json);
                    return new Result(true, StatusCode.OK,"ok",list);
                }
                List<String> cookieList = cookieUtil.getCookieList();
                Result reqFieldResult = entityService.findEntityFields(project.getAlmDomainName(), project.getAlmProjectName(), "requirement", almConfigDao.findAlmConfig(), cookieList);
                //需求属性
                String reqField="";
                //测试类型
                String testType="";
                int testDoc=0;
                int testCode=0;
                int testStatic=0;
                int testluoji=0;
                int testyuliang=0;

                int testgongneng=0;
                int testxingneng=0;
                int testjiekou=0;
                int testqiangdu=0;
                int testshujuchuli=0;

                int testkehuifuxing=0;
                int testkeanzhuangxing=0;
                int testanquanxing=0;
                int testbianjie=0;
                int testrenji=0;

                List<String> filter = new ArrayList<>();
                List<Integer> dataList = new ArrayList<>();
                if(reqFieldResult.isFlag()){
                    List<EntityField> reqEntityFields=(List)reqFieldResult.getData();
                    for (EntityField entityField: reqEntityFields) {
                        if("需求属性".equals(entityField.getLabel())){
                            reqField=entityField.getName();
                            filter.add(reqField);
                        }
                        if("测试类型".equals(entityField.getLabel())){
                            testType=entityField.getName();
                            filter.add(testType);
                        }
                    }
                }
                Result reqResult = entityService.findEntities(project.getAlmDomainName(), project.getAlmProjectName(), "requirements", almConfigDao.findAlmConfig(), cookieList, filter);
                if(reqResult.isFlag()){
                    List<Map> requirements = (List)reqResult.getData();
                    for (Map map: requirements) {
                        if(map.containsKey(reqField)){
                            String r = (String)map.get(reqField);
                            if("测试用例".equals(r)){
                                String ts = (String)map.get(testType);
                                if("文档审查".equals(ts)) {
                                    testDoc+=1;
                                }
                                if("代码审查".equals(ts)) {
                                    testCode+=1;
                                }
                                if("静态分析".equals(ts)) {
                                    testStatic+=1;
                                }
                                if("逻辑测试".equals(ts)) {
                                    testluoji+=1;
                                }
                                if("余量测试".equals(ts)) {
                                    testyuliang+=1;
                                }
                                if("功能测试".equals(ts)) {
                                    testgongneng+=1;
                                }
                                if("性能测试".equals(ts)) {
                                    testxingneng+=1;
                                }
                                if("接口测试".equals(ts)) {
                                    testjiekou+=1;
                                }
                                if("强度测试".equals(ts)) {
                                    testqiangdu+=1;
                                }
                                if("数据处理测试".equals(ts)) {
                                    testshujuchuli+=1;
                                }
                                if("可恢复性测试".equals(ts)) {
                                    testkehuifuxing+=1;
                                }
                                if("可安装性测试".equals(ts)) {
                                    testkeanzhuangxing+=1;
                                }
                                if("安全性测试".equals(ts)) {
                                    testanquanxing+=1;
                                }
                                if("边界测试".equals(ts)) {
                                    testbianjie+=1;
                                }
                                if("人机交互界面测试".equals(ts)) {
                                    testrenji+=1;
                                }
                            }

                        }
                    }
                }
                dataList.add(testDoc);
                dataList.add(testCode);
                dataList.add(testStatic);
                dataList.add(testluoji);
                dataList.add(testyuliang);

                dataList.add(testgongneng);
                dataList.add(testxingneng);
                dataList.add(testjiekou);
                dataList.add(testqiangdu);
                dataList.add(testshujuchuli);

                dataList.add(testkehuifuxing);
                dataList.add(testkeanzhuangxing);
                dataList.add(testanquanxing);
                dataList.add(testbianjie);
                dataList.add(testrenji);
                String s = JSON.toJSONString(dataList);
                redisUtil.setIfAbsentNoTime(project.getAlmDomainName()+project.getAlmProjectName()+"4",s);
                return new Result(true, StatusCode.OK,"ok",dataList);
            }else {
                return new Result(false, StatusCode.OK,"本地项目不可用（未关联到alm项目）");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("测试用例统计数量错误"+e.getMessage());
            return new Result(false, StatusCode.OK,"失败");
        }
    }

}
