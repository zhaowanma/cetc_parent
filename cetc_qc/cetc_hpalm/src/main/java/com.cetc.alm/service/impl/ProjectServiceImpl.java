package com.cetc.alm.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.alm.dao.*;
import com.cetc.alm.feign.UserClient;
import com.cetc.alm.service.AuthencateService;
import com.cetc.alm.service.EntityService;
import com.cetc.alm.service.ProjectService;
import com.cetc.alm.utils.SiteAdminUtils;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.admin.SysUser;
import com.cetc.model.hpalm.*;
import com.cetc.model.project.Code;
import com.cetc.model.project.Project;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static final Logger logger= LoggerFactory.getLogger(ProjectServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AlmConfigDao almConfigDao;

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private CodeDao codeDao;
    @Autowired
    private EntityService entityService;
    @Autowired
    private AlmSiteConfigDao almSiteConfigDao;

    @Autowired
    private UserClient userClient;

    @Autowired
    private AuthencateService authencateService;

    @Autowired
    private AlmExecLogDao almExecLogDao;


    private final String almProjectCreateExecLogType="almProjectCreate";

    @Override
    public Result findProjects(AlmConfig almConfig, String domainName,List<String> cookieList) {
        List<String> projectList = new ArrayList<>();
        try {
            if(cookieList!=null&&cookieList.size()>0){
                String url = almConfig.getUrl();
                String username = almConfig.getUsername();
                String password = almConfig.getPassword();
                if(url!=null&&!"".equals(url)&&username!=null&&!"".equals(username)&&password!=null&&!"".equals(password)){
                    url=url+"/rest/domains/"+domainName+"/projects";
                    List<MediaType> mediaTypes = new ArrayList<>();
                    mediaTypes.add(MediaType.APPLICATION_JSON);
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.put(HttpHeaders.COOKIE,cookieList);
                    httpHeaders.setAccept(mediaTypes);
                    HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
                    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
                    String projectJsonStr=response.getBody();
                    JSONObject jsonObject = JSON.parseObject(projectJsonStr);
                    Object object = jsonObject.get("Project");

                    if(object instanceof JSONArray){
                        JSONArray projectArray=(JSONArray) object;
                        for(int i=0;i<projectArray.size();i++){
                            JSONObject domainObj=(JSONObject)projectArray.get(i);
                            String projectName=(String)domainObj.get("Name");
                                projectList.add(projectName);
                        }
                    }
                    if(object instanceof JSONObject){
                        JSONObject projectObject=(JSONObject) object;
                        String projectName=(String)projectObject.get("Name");
                            projectList.add(projectName);
                    }
                }
            }
            return new Result(true, StatusCode.OK,"根据域名查询项目成功",projectList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("根据域名查询项目失败,reason: "+e.getMessage());
            return new Result(false, StatusCode.ERROR,"根据域名查询项目失败");
        }
    }

    @Override
    public Result findProjectsByDomainName(String domainName) {
        List<String> cookieList =null;
        AlmConfig almConfig=almConfigDao.findAlmConfig();
        try {
            almConfig = almConfigDao.findAlmConfig();
            cookieList = authencateService.login(almConfig);
            Result projects = findProjects(almConfig, domainName, cookieList);
            return new Result(true,StatusCode.OK,"ok",projects.getData());
        }catch (Exception e){
            logger.error("根据域名查询项目失败");
            return new Result(false,StatusCode.ERROR,"error");

        }finally {
            authencateService.closeSession(almConfig,cookieList);
             authencateService.logout(almConfig,cookieList);
        }

    }

    @Override
    @Async
    public void createAlmProjects(Map map) {
        Dispatch disp=null;
        long projectId=(int)map.get("projectId");
        AlmConfig almConfig=almConfigDao.findAlmConfig();
        try {
            String almTemplateDomain=(String)map.get("almTemplateDomain");
            String almTemplateProject=(String)map.get("almTemplateProject");
            long copyStatus=(int)map.get("copyOptions");
            Boolean testTypeGen=(Boolean)map.get("testTypeGen");
            Project project = projectDao.findOne(projectId);
            Code code = codeDao.findOne(project.getParentId());
            String almDomainName=code.getScope();
            String almProjectName=project.getName();
            AlmSiteConfig almSiteConfig = almSiteConfigDao.findAlmSiteConfig();
            try {
                disp = SiteAdminUtils.getDisp(almSiteConfig);
               almExecLogDao.save(new AlmExecLog("成功登录Alm站点管理系统",new Date(),almProjectCreateExecLogType,projectId,"",true,"","#0bbd87"));
            }catch (Exception e){
                e.printStackTrace();
                almExecLogDao.save(new AlmExecLog("登录Alm站点管理系统失败",new Date(),almProjectCreateExecLogType,projectId,"",false,"","#FF0066"));
                throw new RuntimeException("***********登录Alm站点管理系统失败**********");

            }

            try {
                if(almTemplateDomain!=null&&almTemplateProject!=null){
                    Dispatch.call(disp, "DeactivateProject",almTemplateDomain,almTemplateProject);
                    Dispatch.call(disp,"CreateProjectCopy",almDomainName,almProjectName,almSiteConfig.getDbType()
                            ,almTemplateDomain,almTemplateProject,almSiteConfig.getDbName(),almSiteConfig.getDbUser(),almSiteConfig.getDbPassword(),
                            "", "", 0, 0, copyStatus, 5 );
                    Dispatch.call(disp, "ActivateProject",almTemplateDomain,almTemplateProject);
                    almExecLogDao.save(new AlmExecLog("ALM中在域《"+almDomainName+"》成功创建项目《"+almProjectName+"》（模板域："+almTemplateDomain+"；模板项目："+almTemplateProject+"）",new Date(),almProjectCreateExecLogType,projectId,"",true,"","#0bbd87"));

                }else {
                    Dispatch.call(disp, "CreateProject", almDomainName, almProjectName, almSiteConfig.getDbType(),
                            almSiteConfig.getDbName(), almSiteConfig.getDbUser(),almSiteConfig.getDbPassword(), "",
                            "", 0, 0, 1);
                    almExecLogDao.save(new AlmExecLog("ALM中在域《"+almDomainName+"》成功创建项目《"+almProjectName+"》(未使用模板)",new Date(),almProjectCreateExecLogType,projectId,"",true,"","#0bbd87"));

                }
            }catch (Exception e){
                e.printStackTrace();
                almExecLogDao.save(new AlmExecLog("ALM中在域《"+almDomainName+"》创建项目《"+almProjectName+"》失败",new Date(),almProjectCreateExecLogType,projectId,"",false,"","#FF0066"));
                throw new RuntimeException("***********ALM中创建项目失败**********");
            }

          try {
              authencateService.addUserProject(disp,almDomainName,almProjectName,almConfig.getUsername());
              authencateService.addUserGroup(disp,almDomainName,almProjectName,almConfig.getUsername());
              almExecLogDao.save(new AlmExecLog("添加通用配置用户"+almConfig.getUsername()+"成功",new Date(),almProjectCreateExecLogType,projectId,"",true,"","#0bbd87"));

          }catch (Exception e){
              e.printStackTrace();
              logger.error("***********alm添加通用用户失败**********"+e.getMessage());
              almExecLogDao.save(new AlmExecLog("添加通用配置用户失败",new Date(),almProjectCreateExecLogType,projectId,"",false,"","#FF0066"));
              throw new RuntimeException("***********alm添加通用用户失败**********");
          }

            try{
                Map<String, String> almUsers = authencateService.findAlmUsers(disp);
                String header = code.getHeader();
                String leader = project.getLeader();
                String testLeader=project.getTestLeader();
                List<String> projectusernames= new ArrayList<>();
                if(header!=null&&!"".equals(header)){
                    projectusernames.add(header);
                }
                if(leader!=null&&!"".equals(leader)){
                    projectusernames.add(leader);
                }
                if(testLeader!=null&&!"".equals(testLeader)){
                    projectusernames.add(testLeader);
                }
                projectusernames.add(code.getCreateBy());
                Result userByList = userClient.findUserByList(projectusernames);
                String s = JSON.toJSONString(userByList.getData());
                List<SysUser> sysUsers=JSON.parseArray(s,SysUser.class);
                for (SysUser sysUser: sysUsers) {
                    if(almUsers.containsKey(sysUser.getFullname())){
                        authencateService.addUserProject(disp,almDomainName,almProjectName,sysUser.getFullname());
                        authencateService.addUserGroup(disp,almDomainName,almProjectName,sysUser.getFullname());
                    }
                }
                almExecLogDao.save(new AlmExecLog("添加项目用户成功",new Date(),almProjectCreateExecLogType,projectId,"",true,"","#0bbd87"));
            }catch (Exception e){
                e.printStackTrace();
                logger.error("***********alm添加业务用户失败**********"+e.getMessage());
                almExecLogDao.save(new AlmExecLog("alm添加项目用户失败",new Date(),almProjectCreateExecLogType,projectId,"",false,"","#FF0066"));
                throw new RuntimeException("***********alm添加项目用户失败**********");
            }
            List<EntityField> reqFileds=null;
            List<String> cookieList=null;
            try {
                cookieList = authencateService.login(almConfig);
                Result reqField = entityService.findEntityFields(almDomainName, almProjectName, "requirement", almConfig, cookieList);
                reqFileds = (List)reqField.getData();
            }catch (Exception e){
                e.printStackTrace();
                logger.error("***********alm生成根需求失败**********"+e.getMessage());
            }finally {
                if(cookieList!=null){
                    authencateService.logout(almConfig,cookieList);
                }
            }

      try {
           String testType = project.getTestType();
           if(testType!=null&&!"".equals(testType)&&testTypeGen){
                   List<String> testTypes=JSON.parseArray(testType,String.class);
               for (String testTypeName: testTypes) {
                          createReqType(almDomainName,almProjectName,reqFileds,"requirement",testTypeName);
                      }
                   }
                      almExecLogDao.save(new AlmExecLog("alm生成根需求成功",new Date(),almProjectCreateExecLogType,projectId,"",true,"","#0bbd87"));
                 }catch (Exception e){
                     e.printStackTrace();
                     logger.error("***********alm生成根需求失败**********"+e.getMessage());
                     almExecLogDao.save(new AlmExecLog("alm生成根需求失败",new Date(),almProjectCreateExecLogType,projectId,"",false,"","#FF0066"));
                     throw new RuntimeException("***********alm生成根需求失败**********");
        }

        }catch (Exception e){
            e.printStackTrace();
            logger.error("创建alm项目失败****************"+e.getMessage());
        }finally {
            if(disp!=null){
                Dispatch.call(disp, "Logout");
                ComThread.InitSTA();
            }
            almExecLogDao.save(new AlmExecLog("退出ALM站点管理系统",new Date(),almProjectCreateExecLogType,projectId,"",true,"","#0bbd87"));
        }
    }

    @Override
    public Result findAlmProjectCreateLog(long id) {
        List<AlmExecLog> execLogs = almExecLogDao.findByBusinsessType(almProjectCreateExecLogType, id);
        if ((execLogs!=null&&execLogs.size()>0)){
            for (AlmExecLog almExecLog: execLogs) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String format = sdf.format(almExecLog.getExecTime());
                almExecLog.setExecTimeStr(format);
            }
        }
        return new Result(true,StatusCode.OK,"ok",execLogs);
    }


    //生成根测试类型
    Boolean createReqType(String domainName,String projectName,List<EntityField> reqs, String entityType, String name){
        AlmConfig almConfig = almConfigDao.findAlmConfig();
        List<String> cookieList = authencateService.login(almConfig);
        try {

            String prefix="<Entity Type=\'"+entityType.toLowerCase()+"\'>"+"<Fields>";
            for (EntityField reqField : reqs) {
                if("名称".equals(reqField.getLabel())) {
                    String xmlItem="<Field Name=\'"+reqField.getName()+"\'>"+"<Value>"+name+"</Value></Field>";
                    prefix+=xmlItem;
                }
                if("需求类型".equals(reqField.getLabel())) {
                    String xmlItem="<Field Name=\'"+reqField.getName()+"\'>"+"<Value>"+"0"+"</Value></Field>";
                    prefix+=xmlItem;
                }
            }
            prefix=prefix+"</Fields></Entity>";
            String url=almConfigDao.findAlmConfig().getUrl()+"/rest/domains/"+domainName+"/projects/"+projectName+"/"+entityType+"s";
            Result entityCreate= entityService.createEntity(prefix, url, cookieList);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("****************生成根需求失败"+name);
        }finally {
            if(cookieList!=null){
                authencateService.logout(almConfig,cookieList);
            }
        }
    }

}
