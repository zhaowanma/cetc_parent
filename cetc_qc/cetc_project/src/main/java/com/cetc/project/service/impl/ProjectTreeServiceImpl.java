package com.cetc.project.service.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.model.project.Code;
import com.cetc.model.project.Kingdom;
import com.cetc.model.project.Project;
import com.cetc.model.project.TreeData;
import com.cetc.project.entities.SearchCode;
import com.cetc.project.entities.SearchProject;
import com.cetc.project.mapper.*;
import com.cetc.project.service.ProjectTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectTreeServiceImpl implements ProjectTreeService {

    @Autowired
   KingdomDao kingdomDao;

   @Autowired
   CodeDao codeDao;

   @Autowired
   ProjectDao projectDao;

   @Autowired
   CodeVisitableDao codeVisitableDao;

   @Autowired
   ProjectVisitableDao projectVisitableDao;

    @Override
    public Result findTreeData() {
        String username = LoginUserUtil.getLoginSysUser().getUsername();
        List<Object> list = new ArrayList<>();
        TreeData treeData = new TreeData();
         treeData.setId((long)-1);
         treeData.setLabel("项目管理");
         treeData.setName("项目管理");
         treeData.setOperate(true);
        List<Kingdom> kingdoms = kingdomDao.findAll();
        Code searchCode = new Code();
        searchCode.setStatus(true);
        List<Code> codes = codeDao.findCodeByField(searchCode);
        Project searchProject = new Project();
        searchProject.setStatus(true);
        List<Project> projects = projectDao.findProjectByField(searchProject);
        List kingdomTreeDatas = new ArrayList();
        for (Kingdom kingdom: kingdoms) {
            TreeData kingdomTreeData = new TreeData();
            kingdomTreeData.setId(kingdom.getId());
            kingdomTreeData.setLabel(kingdom.getName());
            kingdomTreeData.setName(kingdom.getName());
            kingdomTreeData.setOperate(true);
            kingdomTreeDatas.add(kingdomTreeData);
            List<TreeData> codeTreeDatas = new ArrayList<>();
            for (Code code: codes) {
                if(kingdom.getId().longValue()==code.getParentId().longValue()){
                    TreeData codeTreeData = new TreeData();
                    codeTreeData.setId(code.getId());
                    codeTreeData.setName(code.getName());
                    codeTreeData.setLabel(code.getValue());
                    List<String> usernames = codeVisitableDao.findUsernamesByCodeId(code.getId());
                    List<TreeData> projectTreeDatas = new ArrayList<>();
                    if(username.equals(code.getCreateBy())||username.equals(code.getHeader())||username.equals(code.getLeader())||usernames.contains(username)){
                        codeTreeData.setOperate(true);
                        codeTreeDatas.add(codeTreeData);
                        for (Project project: projects) {
                            if(code.getId().longValue()==project.getParentId().longValue()){
                                TreeData projectTreeData = new TreeData();
                                projectTreeData.setId(project.getId());
                                projectTreeData.setLabel(project.getName());
                                projectTreeData.setName(project.getName());
                                projectTreeData.setOperate(true);
                                projectTreeDatas.add(projectTreeData);
                            }
                        }
                    }else {
                        codeTreeData.setOperate(false);
                        codeTreeDatas.add(codeTreeData);
                        for (Project project: projects) {
                            if(code.getId().longValue()==project.getParentId().longValue()){
                                List<String> usernamesByProjectId = projectVisitableDao.findUsernamesByProjectId(project.getId());
                                List<String> joins = projectDao.findJoins(project.getId());
                                TreeData projectTreeData = new TreeData();
                                projectTreeData.setId(project.getId());
                                projectTreeData.setLabel(project.getName());
                                projectTreeData.setName(project.getName());
                                if(username.equals(project.getCreateBy())||username.equals(project.getTestLeader())|| username.equals(project.getLeader())||usernamesByProjectId.contains(username)||joins.contains(username)){
                                    projectTreeData.setOperate(true);
                                }else {
                                    projectTreeData.setOperate(false);
                                }
                                projectTreeDatas.add(projectTreeData);
                            }
                        }
                    }

                  codeTreeData.setChildren(projectTreeDatas);

                }
            }
            kingdomTreeData.setChildren(codeTreeDatas);

        }
       treeData.setChildren(kingdomTreeDatas);
        list.add(treeData);
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    @Override
    public Result findMyTreeData(List<TreeData> projectList) {
        for (TreeData treeData: projectList) {
            List<TreeData> kingdoms = treeData.getChildren();
            if(kingdoms!=null&&kingdoms.size()>0){
                for (int i=0;i<kingdoms.size();i++) {
                    TreeData treeData1 = kingdoms.get(i);
                    if (!treeData1.getOperate()) {
                        kingdoms.remove(treeData1);
                        i--;
                    }
                    List<TreeData> codes = treeData1.getChildren();
                    if (codes != null){
                        for (int j=0;j<codes.size();j++) {
                            TreeData treeData2 = codes.get(j);
                            Boolean codeOperate = treeData2.getOperate();
                            List<TreeData> projects = treeData2.getChildren();
                            Boolean codeRemove = true;
                            if(projects!=null) {
                                for (int k=0;k<projects.size();k++) {
                                    TreeData treeData3 = projects.get(k);
                                    if (treeData3.getOperate()) {
                                        codeRemove = false;
                                    } else {
                                        projects.remove(treeData3);
                                        k--;
                                    }
                                }
                            }
                            if (!codeOperate && codeRemove) {
                                codes.remove(treeData2);
                                j--;
                            }
                        }
                }
                }
            }
        }
        return new Result(true,StatusCode.OK,"查询我的项目成功",projectList);
    }
}
