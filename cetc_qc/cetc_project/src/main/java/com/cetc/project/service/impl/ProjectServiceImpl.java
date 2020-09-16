package com.cetc.project.service.impl;


import com.cetc.common.core.entity.DocumentLevel;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.model.dic.DicValue;
import com.cetc.model.notice.Notice;
import com.cetc.model.project.*;
import com.cetc.project.entities.*;
import com.cetc.project.feign.DicClient;
import com.cetc.project.feign.ProcessClient;
import com.cetc.project.mapper.*;
import com.cetc.project.sender.Sender;
import com.cetc.project.service.ProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {

    private Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private CodeDao codeDao;
    @Autowired
    private KingdomDao kingdomDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private TestTypeDicDao testTypeDicDao;
    @Autowired
    private HmilyDao hmilyDao;
    @Autowired
    private Sender sender;
    @Autowired
    private ProcessClient processClient;
    @Autowired
    private DicClient dicClient;
    @Autowired
    private ProjectVisitableDao projectVisitableDao;
    @Autowired
    private CodeVisitableDao codeVisitableDao;
    @Autowired
    private DocumentCheckDao documentCheckDao;
    @Autowired
    private SqaDao sqaDao;
    @Autowired
    private AnnotationDao annotationDao;
    @Autowired
    private ProjectExcuteDao projectExcuteDao;
    @Autowired
    private CodeTestDao codeTestDao;

    @Value("${ACTIVITI.MODEL.PROJECT.KEY}")
    private String model_key;

    @Override
    @Transactional
    public Result save(Project project) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Code code = codeDao.findOne(project.getParentId());
        project.setKingdom(code.getKingdom());
        project.setKingdomId(code.getParentId());
        Date current = new Date();
        project.setUpdateBy(principal);
        project.setUpdateDate(current);
        if(project.getId() == null){
            project.setLeader(code.getLeader());  //设置总体负责人，为令号的负责人
            project.setCreateBy(principal);
            project.setCreateDate(current);
            project.setKingdomId(code.getParentId());
            project.setKingdom(code.getKingdom());
            String lastNum = projectDao.getLastNum();
            if(lastNum!=null){
                int last = Integer.parseInt(lastNum.substring(0,6));
                int num = Integer.parseInt(project.getNum().substring(0,6));
                boolean flag = last<=num;
                if(flag){
                    last++;
                    project.setNum(last+project.getNum().substring(6));
                }
            }
            projectDao.addProject(project);
        }else{
            //更新
            testTypeDicDao.delptd(project.getId());
            projectDao.update(project);
        }
        List<Long> testType = project.getTestType();
        if(testType!=null){
            for (Long id : testType) {
                testTypeDicDao.addptd(project.getId(),id);
            }
        }
        return new Result(true,StatusCode.OK,"");
    }
    @Override
    public Result pageByParent(SearchProject searchProject) {
        PageHelper.startPage(searchProject.getPageNum(),searchProject.getPageSize());
        List<Project> projectByKingdom = projectDao.queryList(searchProject);
        PageInfo<Project> pageInfo = new PageInfo<>(projectByKingdom);
        return new Result(true,StatusCode.OK,"",pageInfo);
    }
    @Override
    public Result getTestTypeList(Long id) {
        List<Long> ids = new ArrayList<>();
        List<TestTypeDic> list = testTypeDicDao.findByProjectId(id);
        for (TestTypeDic t : list) {
            ids.add(t.getDicId());
        }
        return new Result(true,StatusCode.OK,"",ids);
    }
    @Override
    public Result getNum(Long codeId) {
        String num = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        num = sdf.format(new Date());
        return new Result(true,StatusCode.OK,"",num);
    }
    @Override
    @Transactional
    public Result delProject(Long id) {
        //删除测试类型
        testTypeDicDao.delptd(id);
        //删除参与人员
        projectDao.delJoins(id);
        //删除可视人员表
        projectVisitableDao.deleteByProjectId(id);
        //删除文档
        SearchDocumentCheck searchDocumentCheck = new SearchDocumentCheck();
        searchDocumentCheck.setCheckLevel("p");
        searchDocumentCheck.setProjectId(id);
        List<DocumentCheck> documentCheckList = documentCheckDao.queryList(searchDocumentCheck);
        for (DocumentCheck documentCheck : documentCheckList) {
            //删除文档下的问题类型
            SearchAnnotation searchAnnotation  = new SearchAnnotation();
            searchAnnotation.setParentId(documentCheck.getId());
            annotationDao.deleteAnnotation(searchAnnotation);
        }
        documentCheckDao.deleteDocumentCheck(searchDocumentCheck);
        //删除测试执行
        SearchProjectExcute searchProjectExcute = new SearchProjectExcute();
        searchProjectExcute.setProjectId(id);
        projectExcuteDao.deleteProjectExcute(searchProjectExcute);
        //删除项目测试类型表
        testTypeDicDao.delptd(id);
        //删除代码测试
        SearchCodeTest searchCodeTest = new SearchCodeTest();
        searchCodeTest.setParentId(id);
        codeTestDao.deleteCodeTest(searchCodeTest);
        //删除项目
        projectDao.deleteOne(id);
        return new Result(true,StatusCode.OK,"");
    }

    @Override
    public Result queryList(SearchProject searchProject) {
        List<Project> projectList = projectDao.queryList(searchProject);
        return new Result(true,StatusCode.OK,"",projectList);
    }

    @Override
    public Result findProjectData() {
        int projects=0;
        int myprojects=0;
        int projectszy=0;
        int myprojectszy=0;
        Project project1 = new Project();
        project1.setStatus(true);
        List<Project> list1 = projectDao.findProjectByField(project1);
        projects=list1.size();

        String username = LoginUserUtil.getLoginSysUser().getUsername();
        Project project2 = new Project();
        project2.setStatus(true);
        project2.setTestLeader(username);
        List<Project> list2 = projectDao.findProjectByField(project2);
        myprojects=list2.size();

        Project project3 = new Project();
        project3.setStatus(true);
        project3.setZy(true);
        List<Project> list3 = projectDao.findProjectByField(project3);
        projectszy=list3.size();

        Project project4 = new Project();
        project4.setStatus(true);
        project4.setZy(true);
        project4.setTestLeader(username);
        List<Project> list4 = projectDao.findProjectByField(project4);
        myprojectszy=list4.size();

        Map<Object, Object> map = new HashMap<>();
        map.put("projects",projects);
        map.put("myprojects",myprojects);
        map.put("projectszy",projectszy);
        map.put("myprojectszy",myprojectszy);
        return new Result(true,StatusCode.OK,"ok",map) ;
    }

    @Override
    public Result countOfMonth(Map map) {
        Integer[] projectArray = new Integer[12];
        Calendar calendar = Calendar.getInstance();
        if (map.get("year") != null && !"".equals(map.get("year"))) {
            calendar.set(Calendar.YEAR, (Integer)map.get("year") );
        }
        for (int i = 0; i < 12; i++) {
            calendar.set(Calendar.MONTH, i);  //设置月份
            calendar.set(Calendar.DAY_OF_MONTH, 1); //设置日期
            calendar.set(Calendar.HOUR_OF_DAY, 0);//设置小时
            calendar.set(Calendar.MINUTE, 0);//设置分钟
            calendar.set(Calendar.SECOND, 0);//设置秒
            Date start = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            Date end = calendar.getTime();
            Map<Object, Object> queryMap = new HashMap<>();
            //令号
            queryMap.put("status",true);
            queryMap.put("start",start);
            queryMap.put("end",end);
            projectArray[i] = projectDao.fuzzyQueryList(queryMap).size();
        }
        return new Result(true, StatusCode.OK, "", projectArray);
    }

    @Override
    public Result handleProjectIsZy(Project project) {
        projectDao.update(project);
        if(project.getZy()){
            Long codeId = project.getParentId();
            Code code = codeDao.findOne(codeId);
            if(code!=null&&!code.getZy()){
                code.setZy(true);
                codeDao.updateCode(code);
            }
        }
        return new Result(true,StatusCode.OK,"OK");
    }

    @Override
    public Result findByCode(Long id) {
        SearchProject searchProject = new SearchProject();
        searchProject.setParentId(id);
        List<Project> projectList = projectDao.queryList(searchProject);
        return new Result(true, StatusCode.OK, "", projectList);
    }

    @Transactional
    //只要标记@Hmily就是try方法，在注解中指定confirm、cancel两个方法的名字
    @Hmily(confirmMethod="commit",cancelMethod="rollback")
    public Result commitProjectApply(Map params) {
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("提交申请 try begin 开始执行...xid:{}",transId);
        //幂等判断 判断local_try_log表中是否有try日志记录，如果有则不再执行
        if(hmilyDao.isExistTry(transId)>0){
            log.info("bank1 try 已经执行，无需重复执行,xid:{}",transId);
            return null ;
        }
        //try悬挂处理，如果cancel、confirm有一个已经执行了，try不再执行
        if(hmilyDao.isExistConfirm(transId)>0 || hmilyDao.isExistCancel(transId)>0){
            log.info("bank1 try悬挂处理  cancel或confirm已经执行，不允许执行try,xid:{}",transId);
            return null;
        }
        String checkedUsername=(String)params.get("checkedUsername");
        String notes=(String)params.get("notes");
        long businesskey=Long.valueOf(params.get("businesskey").toString());
        Map<String, Object> variables = new HashMap<>();
        try {
            Project project = new Project();
            project.setId(businesskey);
            project.setApply(true);
            project.setRemark(notes);
            projectDao.update(project);
           Project one = projectDao.findOne(businesskey);
            String startUser = LoginUserUtil.getLoginSysUser().getUsername();
            variables.put("start_user",startUser);
            variables.put("check_user",checkedUsername);
            variables.put("title",one.getName());
        }catch (Exception e){
            throw new RuntimeException("apply try 修改项目审批申请单失败,xid:{}"+transId);
        }
        //插入try执行记录,用于幂等判断
        hmilyDao.addTry(transId);
        //调用流程服务的开启流程实例接口
        if(!processClient.startProcessInstance(model_key, Long.valueOf(businesskey), variables).isFlag()){
            throw new RuntimeException("开启项目创建流程实例失败，服务降级");
        }
        return new Result(true,StatusCode.OK,"提交申请成功");
    }

    @Override
    public Result findProjectById(long id) {
        Project project = projectDao.findOne(id);
        if(project!=null){
            List<String> joins = projectDao.findJoins(id);
            project.setJoins(joins);
        }

        return new Result(true,StatusCode.OK,"OK",project);
    }

    @Override
    @Transactional
    public Result updateProject(Project project) {
        try {
            projectDao.update(project);
            return new Result(true,StatusCode.OK,"修改项目信息成功");
        }catch (Exception e){
            e.printStackTrace();
            log.error("修改项目数据失败");
            return new Result(false,StatusCode.ERROR,"修改项目信息成功");
        }

    }

    @Override
    @Transactional
    public Result updateProjectAndJoins(Project project) {
        //删除历史参与人员可视令号和项目
        projectDao.update(project);
        projectDao.delJoins(project.getId());
        List<String> joins = project.getJoins();
        //更新新数据
        if(joins!=null&&!"".equals(joins)){
            for (String join : joins) {
                projectDao.addJoins(project.getId(),join);
            }
        }
        return new Result(true,StatusCode.OK,"保存成功");
    }
    @Override
    @Transactional
    public Result setVisual(Project project, List<String> username) {
        List<String> usernamesByProjectId = projectVisitableDao.findUsernamesByProjectId(project.getId());
        usernamesByProjectId.removeAll(username);
        for (String s : usernamesByProjectId) {
            projectVisitableDao.deleteByUsernameAndProjectId(project.getId(),s);
        }
        for (String joinName: username) {
            Project project1 = projectVisitableDao.findProjectByProjectIdAndUsername(project.getId(), joinName);
            if(project1==null){
               projectVisitableDao.insertProjectUsername(project.getId(),joinName);
            }
        }
        return new Result(true,StatusCode.OK,"");
    }
    @Override
    public Result findVisual(Long projectId) {
        List<String> userList = projectVisitableDao.findUsernamesByProjectId(projectId);
        return new Result(true,StatusCode.OK,"",userList);
    }
    @Override
    public Result selectProjectByKingdomId(Long kingdomId) {
        List<Code> codes = codeDao.findByKingdom(kingdomId);
        ArrayList<Object> objects = new ArrayList<>();
        if(codes != null && !codes.isEmpty()){
            ArrayList<String> keys = new ArrayList<>();
            ArrayList<Integer> values = new ArrayList<>();
            int m = 0;
            for (Code code : codes) {
                SearchProject searchProject = new SearchProject();
                searchProject.setParentId(code.getId());
                List<Project> projects = projectDao.queryList(searchProject);
                keys.add(m,code.getValue());
                if(projects != null && !projects.isEmpty()){
                    values.add(m,projects.size());
                }else{
                    values.add(m,0);
                }
            }
            objects.add(keys);
            objects.add(values);
            return new Result(true, StatusCode.OK,"",objects);
        }
        return new Result(true, StatusCode.OK,"",objects);
    }

    /**
     * ??????
     * @return
     */
    @Override
    public Result getProjectCountBykingdom() {
        List<Kingdom> kingdoms = kingdomDao.findAll();
        ArrayList<Object> objects = new ArrayList<>();
        if(kingdoms != null && !kingdoms.isEmpty()){
            ArrayList<String> keys = new ArrayList<>();
            ArrayList<Integer> values = new ArrayList<>();
            int m = 0;
            for (Kingdom kingdom : kingdoms) {
                SearchProject searchProject = new SearchProject();
                searchProject.setKingdomId(kingdom.getId());
                List<Project> projects = projectDao.queryList(searchProject);
                keys.add(m,kingdom.getName());
                if(projects != null && !projects.isEmpty()){
                    values.add(m,projects.size());
                }else{
                    values.add(m,0);
                }
            }
            objects.add(keys);
            objects.add(values);
            return new Result(true, StatusCode.OK,"",objects);
        }
        return new Result(true, StatusCode.OK,"",objects);
    }
    @Override
    public Result getProjectCountByTestGrade(SearchProject searchProject) {
        List<Map<String, Object>> testDiclist = (List)dicClient.findByName("testGrade").getData();
        Map<String, Integer> map = new HashMap<>();
        for (Map<String, Object> stringObjectMap : testDiclist) {
            searchProject.setStatus(true);
            searchProject.setTestGrade((String) stringObjectMap.get("value"));
            List<Project> list = projectDao.queryList(searchProject);
            map.put((String) stringObjectMap.get("value"),list.size());
        }
        return new Result(true,StatusCode.OK,"",map);
    }
    @Override
    public Result selectProjectByKingdomIdAndTestGrade(List<DicValue> dicValueLis, Long kingdomId) {
        ArrayList<Object> objects = new ArrayList<>();
        if(dicValueLis != null && !dicValueLis.isEmpty()){
            ArrayList<String> keys = new ArrayList<>();
            ArrayList<Integer> values = new ArrayList<>();
            SearchProject searchProject = new SearchProject();
            int m = 0;
            for (DicValue dicValue : dicValueLis) {
                searchProject.setTestGrade(dicValue.getValue());
                searchProject.setKingdomId(kingdomId);
                List<Project> projects = projectDao.queryList(searchProject);
                keys.add(m,dicValue.getKey());
                if(projects != null && !projects.isEmpty()){
                    values.add(m,projects.size());
                }else{
                    values.add(m,0);
                }
            }
            objects.add(keys);
            objects.add(values);
            return new Result(true, StatusCode.OK,"",objects);
        }
        return new Result(true, StatusCode.OK,"",objects);
    }
    @Override
    public Result checkRole(long id) {
        Project project = projectDao.findOne(id);
        Code code = codeDao.findOne(project.getParentId());
        /*** 令号的创建者*/
        String createBy = code.getCreateBy();
        /*** 令号的组长*/
        String header = code.getHeader();
        /*** 令号的总体负责人*/
        String leader = code.getLeader();
        /*** 令号的可访问权限组*/
        List<String> usernames = codeVisitableDao.findUsernamesByCodeId(project.getParentId());
        /*** 登录用户名*/
        String username = LoginUserUtil.getLoginSysUser().getUsername();
        if (username.equals(createBy)||username.equals(header)||username.equals(leader)||usernames.contains(username)){
            return new Result(true,StatusCode.OK,"Ok",true);
        }else {
            /*** 项目的创建者*/
            String createBy1 = project.getCreateBy();
            /*** 测试负责人*/
            String testLeader = project.getTestLeader();
            /*** 参与人员*/
            List<String> joins = projectDao.findJoins(id);
            /*** 项目的可访问权限组*/
            List<String> usernamesByProjectId = projectVisitableDao.findUsernamesByProjectId(id);
            if(username.equals(createBy1)||username.equals(testLeader)||joins.contains(username)||usernamesByProjectId.contains(username)){
                return new Result(true,StatusCode.OK,"Ok",true);
            }
        }
        return new Result(true,StatusCode.OK,"Ok",false);

    }

    @Override
    public Result findReadyProjectByParentId(Long codeId) {
        SearchProject searchProject = new SearchProject();
        searchProject.setParentId(codeId);
        searchProject.setStatus(true);
        List<Project> projectList = projectDao.queryList(searchProject);
        return new Result(true,StatusCode.OK,"",projectList);
    }

    @Override
    public Result countByParam(SearchProject searchProject) {
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.YEAR);
        calendar.set(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        searchProject.setStart(calendar.getTime());
        calendar.add(Calendar.YEAR,1);
        searchProject.setEnd(calendar.getTime());
        if("".equals(searchProject.getJoin())||searchProject.getJoin()==null){
            List<Project> list = projectDao.queryList(searchProject);
            return new Result(true,StatusCode.OK,"",list.size());
        }else{//参与的项目
           Set<Long> set = new HashSet<>();
           //查询创建的项目
            SearchProject search1 = new SearchProject();
            search1.setStatus(true);
            search1.setCreateBy(searchProject.getJoin());
            List<Project> list1 = projectDao.queryList(search1);
            for (Project project : list1) {
                set.add(project.getId());
            }
            SearchProject search2 = new SearchProject();
            search2.setStatus(true);
            search2.setTestLeader(searchProject.getJoin());
            List<Project> list2 = projectDao.queryList(search2);
            for (Project project : list2) {
                set.add(project.getId());
            }
            List<Project> list3 = projectVisitableDao.findProjectsByUsername(searchProject.getJoin());
            for (Project project : list3) {
                if(project.getStatus()==true){
                    set.add(project.getId());
                }
            }
            return new Result(true,StatusCode.OK,"",set.size());
        }

    }

    @Override
    public Result countProjectComprehensiveOfMonth(SearchProject searchProject) {
        Integer[] documentArray = new Integer[12];
        Integer[] sqaArray = new Integer[12];
        Integer[] projectArray = new Integer[12];
        Map<String,Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        if(searchProject.getYear()!=null&&!"".equals(searchProject.getYear())){
            calendar.set(Calendar.YEAR,searchProject.getYear());
        }
        for(int i=0;i<12;i++){
            calendar.set(Calendar.MONTH,i);  //设置月份
            calendar.set(Calendar.DAY_OF_MONTH,1); //设置日期
            calendar.set(Calendar.HOUR_OF_DAY,0);//设置小时
            calendar.set(Calendar.MINUTE,0);//设置分钟
            calendar.set(Calendar.SECOND,0);//设置秒
            Date start = calendar.getTime();
            calendar.add(Calendar.MONTH,1);
            Date end = calendar.getTime();
            //文档审查
            SearchDocumentCheck searchDocumentCheck = new SearchDocumentCheck();
            searchDocumentCheck.setCodeId(searchProject.getParentId());
            searchDocumentCheck.setStart(start);
            searchDocumentCheck.setEnd(end);
            searchDocumentCheck.setCheckLevel(DocumentLevel.codeLevel);
            documentArray[i] = documentCheckDao.queryList(searchDocumentCheck).size();
            //SQA
            SQA sqa = new SQA();
            sqa.setCodeId(searchProject.getParentId());
            sqa.setCreateDate(start);
            sqa.setUpdateDate(end);
            sqaArray[i]=sqaDao.pageShow(sqa).size();
            //项目
            searchProject.setStart(start);
            searchProject.setEnd(end);
            projectArray[i] = projectDao.queryList(searchProject).size();

        }
        map.put("document",documentArray);
        map.put("sqa",sqaArray);
        map.put("project",projectArray);
        return new Result(true,StatusCode.OK,"",map);
    }

    @Override
    public Result hasProject(Project project) {
        List<Project> list = projectDao.findProjectByField(project);
        return new Result(true,StatusCode.OK,"",list.size()>0);
    }

    @Override
    public Result countOfWeek(SearchProject searchProject) {
        Integer[] array = new Integer[7];
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.setFirstDayOfWeek(Calendar.MONDAY); //设置每周的第一天
        calendar.setTimeInMillis(System.currentTimeMillis());//设置当前日期
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.add(Calendar.WEEK_OF_MONTH,-1);
        for(int i=0;i<7;i++){
            searchProject.setStart(calendar.getTime());
            calendar.add(Calendar.DAY_OF_WEEK,1);
            searchProject.setEnd(calendar.getTime());
            List<Project> list = projectDao.queryList(searchProject);
            array[i]=list.size();
        }
        return new Result(true,StatusCode.OK,"",array);
    }

    @Override
    public Result findProjectsByStatusReady(SearchProject searchProject) {
        PageHelper.startPage(searchProject.getPageNum(),searchProject.getPageSize());
        Project project = new Project();
        project.setName(searchProject.getName());
        project.setNum(searchProject.getNum());
        project.setZy(searchProject.getZy());
        project.setAlmProjectName(searchProject.getAlmProjectName());
        project.setStatus(true);
        List<Project> projectList = projectDao.findProjectByField(project);
        PageInfo<Project> pageInfo = new PageInfo<>(projectList);
        return new Result(true,StatusCode.OK,"成功",pageInfo);
    }

    //confirm方法
    @Transactional
    public void commit(Map params){
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("apply confirm begin 开始执行...xid:{}",transId);
        if(hmilyDao.isExistConfirm(transId)>0){
            log.info("apply confirm 已经执行，无需重复执行...xid:{}",transId);
            return ;
        }
        String checkedUsername=(String)params.get("checkedUsername");
        Notice notice = new Notice("系统通知", "您有一个待审批的令号审批流程，请尽快查阅。。。", checkedUsername, "系统通知", new Date());
        sender.send(notice);
        hmilyDao.addConfirm(transId);
    }

    /** cancel方法
     * 	cancel幂等校验
     * 	cancel空回滚处理
     * 	修改申请单状态
     *
     */
    @Transactional
    public void rollback(Map params){
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("apply cancel begin 开始执行...xid:{}",transId);
        //	cancel幂等校验
        if(hmilyDao.isExistCancel(transId)>0){
            log.info("apply cancel 已经执行，无需重复执行,xid:{}",transId);
            return;
        }
        //cancel空回滚处理，如果try没有执行，cancel不允许执行
        if(hmilyDao.isExistTry(transId)<=0){
            log.info("apply空回滚处理，try没有执行，不允许cancel执行,xid:{}",transId);
            return;
        }
        long businesskey=(long)params.get("businesskey");
        Project project = new Project();
        project.setId(businesskey);
        project.setApply(true);
        projectDao.update(project);
        hmilyDao.addCancel(transId);
        log.info("apply cancel begin 执行完毕...xid:{}",transId);
    }


}



