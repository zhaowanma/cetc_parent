package com.cetc.project.service.impl;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.model.project.Code;
import com.cetc.model.project.Kingdom;
import com.cetc.model.project.Project;
import com.cetc.project.mapper.CodeDao;
import com.cetc.project.mapper.KingdomDao;
import com.cetc.project.mapper.ProjectDao;
import com.cetc.project.service.CompensateService;
import com.cetc.project.utils.CodeNumUtils;
import com.cetc.project.utils.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CompensateServiceImpl implements CompensateService {

    private static final Logger logger= LoggerFactory.getLogger(CompensateServiceImpl.class);

    @Autowired
    private KingdomDao kingdomDao;

    @Autowired
    private CodeDao codeDao;

    @Autowired
    private ProjectDao projectDao;

    @Override
    public Result importTemplate(MultipartFile file) {
        Boolean checkFile = ExcelUtils.checkFile(file);
        if(!checkFile){
            return new Result(false, StatusCode.ERROR,"文件后缀名只能是xls,i请使用模板生成功能");
        }
        try {
            //获取了模板中的令号数据
            Map<Integer, Object> codeData = ExcelUtils.analysicFile(file,0);
            //获取了模板中的项目数据
            Map<Integer, Object> projectData = ExcelUtils.analysicFile(file,1);
            Boolean codeResult = codeImport(codeData);
            Boolean projectResult = projectImport(projectData);
            if(codeResult&&projectResult){
                return new Result(true,StatusCode.OK,"数据解析成功");
            }else {
                return new Result(false,StatusCode.ERROR,"数据解析失败");
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error("数据解析失败，reason:"+e.getMessage());
            return new Result(false,StatusCode.ERROR,"数据解析失败");
        }
    }

    public Boolean projectImport(Map<Integer, Object> data){
        try {
            if(data!=null){
                Set<Integer> keySet = data.keySet();
                for (int key: keySet) {
                    List cellsList = (List)data.get(key);
                    if(cellsList!=null&&cellsList.size()>0){
                        String projectName=(String)cellsList.get(0);
                        String domainName=(String)cellsList.get(1);
                        String code=(String)cellsList.get(2);
                        String tester=(String)cellsList.get(3);
                        Date assignTime=(Date)cellsList.get(4);
                        String rjzt=(String)cellsList.get(5);
                        String dxzt=(String)cellsList.get(6);
                        String xmzg=(String)cellsList.get(7);
                        String zls=(String)cellsList.get(8);
                        String almDomainName=(String)cellsList.get(9);
                        String almProjectName=(String)cellsList.get(10);
                        String projectNum=(String)cellsList.get(11);
                        String yh=(String)cellsList.get(12);
                        String zy=(String)cellsList.get(13);
                        if(domainName!=null&&!"".equals(domainName)){
                            Kingdom kd = kingdomDao.findByName(domainName);
                            if(kd==null){
                                Kingdom kingdom = new Kingdom();
                                kingdom.setName(domainName);
                                kingdom.setCreateDate(new Date());
                                kingdom.setCreateBy(LoginUserUtil.getLoginSysUser().getUsername());
                                kingdom.setUpdateDate(new Date());
                                kingdom.setUpdateBy(LoginUserUtil.getLoginSysUser().getUsername());
                                kingdomDao.addKingdom(kingdom);
                            }
                            Code code1 = codeDao.findByCodeValue(code);
                            if(code1!=null){
                                Project project = new Project();
                                project.setParentId(code1.getId());
                                project.setName(projectName);
                                List<Project> projectByField = projectDao.findProjectByField(project);
                                if(projectByField==null||projectByField.size()==0){
                                    project.setKingdom(domainName);
                                    kd = kingdomDao.findByName(domainName);
                                    project.setKingdomId(kd.getId());
                                    project.setKingdom(kd.getName());
                                    project.setCode(code1.getValue());
                                    if(projectNum.endsWith("001")){
                                        project.setTestGrade("系统测试");
                                    }else if(projectNum.endsWith("101")) {
                                        project.setTestGrade("配置项测试");
                                    }else if(projectNum.endsWith("201")) {
                                        project.setTestGrade("集成测试");
                                    }else if(projectNum.endsWith("301")) {
                                        project.setTestGrade("单元测试");
                                    }else if(projectNum.endsWith("401")) {
                                        project.setTestGrade("补充测试");
                                    }
                                    project.setTestLeader(tester);
                                    project.setLeader(code1.getLeader());
                                    project.setNum(projectNum);
                                    project.setAlmDomainName(almDomainName);
                                    project.setAlmProjectName(almProjectName);
                                    project.setCreateBy(LoginUserUtil.getLoginSysUser().getUsername());
                                    project.setCreateDate(assignTime);
                                    project.setUpdateBy(LoginUserUtil.getLoginSysUser().getUsername());
                                    project.setUpdateDate(new Date());
                                    project.setCompensate(true);
                                    if("是".equals(yh)){
                                        project.setYh(true);
                                    }else {
                                        project.setYh(false);
                                    }
                                    if("是".equals(zy)){
                                        project.setZy(true);
                                    }else {
                                        project.setZy(false);
                                    }
                                    project.setAssignTime(assignTime);
                                    project.setApply(true);
                                    project.setStatus(true);
                                    projectDao.importTemplateProject(project);
                                }
                            }else {
                                logger.info("令号"+code+"不存在，该令号下的项目无法导入");
                            }

                        }
                    }
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("模板项目数据导入错误，reason"+e.getMessage());
            return false;
        }
    }

    public Boolean codeImport( Map<Integer, Object> data){
           try {
               if(data!=null){
                   Set<Integer> keySet = data.keySet();
                   for (int key: keySet) {
                       List cellsList = (List)data.get(key);
                       if(cellsList!=null&&cellsList.size()>0){
                           String code=(String)cellsList.get(0);
                           String kingdomName = (String)cellsList.get(1);
                           String codeName=(String)cellsList.get(2);
                           String header=(String)cellsList.get(3);
                           String leader=(String)cellsList.get(4);
                           Date assignTime=new Date();
                           if(!"".equals(cellsList.get(5))){
                               assignTime=(Date)cellsList.get(5);
                           }
                           String rjzt=(String)cellsList.get(6);
                           String dxzt=(String)cellsList.get(7);
                           String xmzg=(String)cellsList.get(8);
                           String zls=(String)cellsList.get(9);
                           String yh=(String)cellsList.get(10);
                           String zy=(String)cellsList.get(11);
                           if(kingdomName!=null&&!"".equals(kingdomName)){
                               Kingdom kd = kingdomDao.findByName(kingdomName);
                               if(kd==null){
                                   Kingdom kingdom = new Kingdom();
                                   kingdom.setName(kingdomName);
                                   kingdom.setCreateDate(new Date());
                                   kingdom.setCreateBy(LoginUserUtil.getLoginSysUser().getUsername());
                                   kingdom.setUpdateDate(new Date());
                                   kingdom.setUpdateBy(LoginUserUtil.getLoginSysUser().getUsername());
                                   kingdomDao.addKingdom(kingdom);
                               }
                               Code code1 = codeDao.findByCodeValue(code);
                               if(code1==null){
                                   Code cd = new Code();
                                   cd.setName(codeName);
                                   cd.setValue(code);
                                   Kingdom newKd = kingdomDao.findByName(kingdomName);
                                   cd.setParentId(newKd.getId());
                                   cd.setKingdom(newKd.getName());
                                   cd.setScope(newKd.getName()+code);
                                   cd.setHeader(header);
                                   cd.setLeader(leader);
                                   List<Code> codes = codeDao.findAll();
                                   cd.setCreateDate(assignTime);
                                   Integer num = CodeNumUtils.getNum(codes, assignTime);
                                   cd.setNum(num);
                                   cd.setCreateBy(LoginUserUtil.getLoginSysUser().getUsername());
                                   cd.setUpdateBy(LoginUserUtil.getLoginSysUser().getUsername());
                                   cd.setUpdateDate(new Date());
                                   cd.setApply(true);
                                   cd.setStatus(true);
                                   cd.setCompensate(true);
                                   cd.setAssignTime(assignTime);
                                   if("是".equals(yh)){
                                       cd.setYh(true);
                                   }else {
                                       cd.setYh(false);
                                   }
                                   if("是".equals(zy)){
                                       cd.setZy(true);
                                   }else {
                                       cd.setZy(false);
                                   }
                                   cd.setSoftMan(rjzt);
                                   cd.setSignalMan(dxzt);
                                   cd.setProjectMan(xmzg);
                                   cd.setQualityer(zls);
                                   codeDao.importTemplateCode(cd);

                               }
                           }
                       }
                   }
               }
               return true;
           }catch (Exception e){
               e.printStackTrace();
               logger.error("模板令号数据导入错误，reason"+e.getMessage());
               return false;
           }
    }


}
