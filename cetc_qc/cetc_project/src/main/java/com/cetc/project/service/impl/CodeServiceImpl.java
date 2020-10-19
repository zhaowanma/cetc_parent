package com.cetc.project.service.impl;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.model.notice.Notice;
import com.cetc.model.project.*;
import com.cetc.project.entities.*;
import com.cetc.project.feign.ProcessClient;
import com.cetc.project.mapper.*;
import com.cetc.project.sender.Sender;
import com.cetc.project.service.CodeService;
import com.cetc.project.utils.CodeNumUtils;
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
public class CodeServiceImpl implements CodeService {

    private final static Logger logger= LoggerFactory.getLogger(CodeServiceImpl.class);
    @Autowired
    private CodeDao codeDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private KingdomDao kingdomDao;

    @Autowired
    private HmilyDao hmilyDao;

    @Autowired
    private Sender sender;

    @Autowired
    private ProcessClient processClient;

    @Autowired
    private CodeVisitableDao codeVisitableDao;

    @Autowired
    private DocumentCheckDao documentCheckDao;
    @Value("${ACTIVITI.MODEL.CODE.KEY}")
    private String model_key;

    @Autowired
    private SqaDao sqaDao;

    @Autowired
    private AnnotationDao annotationDao;

    @Autowired
    private ProjectVisitableDao projectVisitableDao;
    @Autowired
    private ProjectExcuteDao projectExcuteDao;
    @Autowired
    private CodeTestDao codeTestDao;

    @Override
    @Transactional
    public Result save(Code code) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date currentDate = new Date();
        code.setUpdateDate(currentDate);
        code.setUpdateBy(principal);
        //新增
        if(code.getId()==null){
            List<Code> codes = codeDao.findAll();
            Integer num = CodeNumUtils.getNum(codes, new Date());
            code.setNum(num);
            code.setCreateBy(principal);
            if(code.getCreateDate()==null){
                code.setCreateDate(currentDate);
            }
            code.setAssignTime(new Date());
            codeDao.addCode(code);
        }else{
            codeDao.updateCode(code);
        }
        return new Result(true, StatusCode.OK,"");
    }


    @Override
    public Result getNum() {
        List<Code> codes = codeDao.findAll();
        Integer num = CodeNumUtils.getNum(codes, new Date());
        return new Result(true,StatusCode.OK,"获取产品序号成功",num);
    }

    @Override
    public Result findPageCode(Map params) {
        PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
        List<Code> codeList = codeDao.fuzzyQueryList(params);
        PageInfo<Code> pageInfo = new PageInfo<>(codeList);
        return new Result(true,StatusCode.OK,"",pageInfo);
    }
    @Override
    public Result findPageCodeWithReady(Map params) {
        PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
        params.put("status",true);
        List<Code> codeList = codeDao.fuzzyQueryList(params);
        PageInfo<Code> pageInfo = new PageInfo<>(codeList);
        return new Result(true,StatusCode.OK,"",pageInfo);
    }

    @Override
    @Transactional
    public Result delete(Long id) {
        //删除SQA
        SearchSQA searchSQA = new SearchSQA();
        searchSQA.setCodeId(id);
        sqaDao.deleteSQA(searchSQA);
        //删除文档审查
        SearchDocumentCheck searchDocumentCheck = new SearchDocumentCheck();
        searchDocumentCheck.setCodeId(id);
        List<DocumentCheck> list = documentCheckDao.queryList(searchDocumentCheck);
        for (DocumentCheck documentCheck : list) {
            //删除文档问题
            SearchAnnotation searchAnnotation = new SearchAnnotation();
            searchAnnotation.setParentId(documentCheck.getId());
            annotationDao.deleteAnnotation(searchAnnotation);
        }
        documentCheckDao.deleteDocumentCheck(searchDocumentCheck);
        //删除项目
        SearchProject searchProject = new SearchProject();
        searchProject.setParentId(id);
        List<Project> projectList = projectDao.queryList(searchProject);
        for (Project project : projectList) {
            //删除项目可视人
            projectVisitableDao.deleteByProjectId(project.getId());
            //删除代码测试
            SearchCodeTest searchCodeTest = new SearchCodeTest();
            searchCodeTest.setParentId(project.getId());
            codeTestDao.deleteCodeTest(searchCodeTest);
            //删除项目参与人
            projectDao.delJoins(project.getId());

        }
        //删除测试执行
        SearchProjectExcute searchProjectExcute = new SearchProjectExcute();
        searchProjectExcute.setCodeId(id);
        projectExcuteDao.deleteProjectExcute(searchProjectExcute);
        //删除项目
        projectDao.deleteProject(searchProject);
        //删除令号可视人
        codeVisitableDao.deleteByCodeId(id);
        //删除令号
        codeDao.deleteOne(id);
        return new Result(true,StatusCode.OK,"");
    }

    @Override
    @Transactional
    public Result setLeader(Long id,String username,List<String> visableList) {
        codeDao.setLeader(id,username);
        List<String> usernamesByCodeId = codeVisitableDao.findUsernamesByCodeId(id);
        usernamesByCodeId.removeAll(visableList);
        if(usernamesByCodeId!=null&&usernamesByCodeId.size()>0){
            for (String name: usernamesByCodeId ) {
                codeVisitableDao.deleteByUsernameAndCodeId(id,name);
            }
        }
        for (String joinName: visableList) {
            Code code = codeVisitableDao.findCodeByCodeIdAndUsername(id, joinName);
            if(code==null){
                codeVisitableDao.insertCodeUsername(id,joinName);
            }
        }
        return new Result(true,StatusCode.OK,"");
    }

    @Override
    public Result countOfKingdom() {
        List<Kingdom> kingdomList = kingdomDao.findAll();
        Map<String,Integer> map = new HashMap<>();
        for (Kingdom kingdom : kingdomList) {
            Code code = new Code();
            code.setParentId(kingdom.getId());
            code.setStatus(true);
            map.put(kingdom.getName(),codeDao.findCodeByField(code).size());
        }
        return new Result(true,StatusCode.OK,"",map);
    }


    @Override
    @Transactional
    //只要标记@Hmily就是try方法，在注解中指定confirm、cancel两个方法的名字
    @Hmily(confirmMethod="commit",cancelMethod="rollback")
    public Result commitCodeApply(Map params) {
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        logger.info("提交申请 try begin 开始执行...xid:{}",transId);
        //幂等判断 判断local_try_log表中是否有try日志记录，如果有则不再执行
        if(hmilyDao.isExistTry(transId)>0){
            logger.info("bank1 try 已经执行，无需重复执行,xid:{}",transId);
            return null ;
        }
        //try悬挂处理，如果cancel、confirm有一个已经执行了，try不再执行
        if(hmilyDao.isExistConfirm(transId)>0 || hmilyDao.isExistCancel(transId)>0){
            logger.info("bank1 try悬挂处理  cancel或confirm已经执行，不允许执行try,xid:{}",transId);
            return null;
        }
        String checkedUsername=(String)params.get("checkedUsername");
        String remarks=(String)params.get("remarks");
        long businesskey=Long.valueOf(params.get("businesskey").toString());
        Map<String, Object> variables = new HashMap<>();
        try {
            //修改令号申请单状态
            Code code = new Code();
            code.setId(businesskey);
            code.setApply(true);
            code.setRemarks(remarks);
            codeDao.updateCode(code);
            Code one = codeDao.findOne(businesskey);
            String startUser = LoginUserUtil.getLoginSysUser().getUsername();
            variables.put("start_user",startUser);
            variables.put("check_user",checkedUsername);
            variables.put("title",one.getValue());

        }catch (Exception e){
            throw new RuntimeException("apply try 修改令号审批申请单失败,xid:{}"+transId);
        }
        //插入try执行记录,用于幂等判断
        hmilyDao.addTry(transId);
        //调用流程服务的开启流程实例接口
        if(!processClient.startProcessInstance(model_key, Long.valueOf(businesskey), variables).isFlag()){
            throw new RuntimeException("开启令号创建流程实例失败，服务降级");
        }
        return new Result(true,StatusCode.OK,"提交申请成功");
    }

    @Override
    public Result findDatas() {
        Map<String, Object> map = new HashMap<>();
        int codes=0;
        int mycodes=0;
        int codezy=0;
        int mycodezy=0;

        Code code1 = new Code();
        code1.setStatus(true);
        List<Code> list1 = codeDao.findCodeByField(code1);
        codes=list1.size();

        String username = LoginUserUtil.getLoginSysUser().getUsername();
        Code code2 = new Code();
        code2.setLeader(username);
        code2.setStatus(true);
        List<Code> list2 = codeDao.findCodeByField(code2);
        mycodes=list2.size();


        Code code3 = new Code();
        code3.setZy(true);
        code3.setStatus(true);
        List<Code> list3 = codeDao.findCodeByField(code3);
        codezy=list3.size();

        Code code4 = new Code();
        code4.setZy(true);
        code4.setStatus(true);
        code4.setLeader(username);
        List<Code> list4 = codeDao.findCodeByField(code4);
        mycodezy=list4.size();
        map.put("codes",codes);
        map.put("mycodes",mycodes);
        map.put("codezy",codezy);
        map.put("mycodezy",mycodezy);
        return new Result(true,StatusCode.OK,"ok",map);
    }

    @Override
    public Result queryList(SearchCode searchCode) {
        List<Code> codeList = codeDao.queryList(searchCode);
        return new Result(true,StatusCode.OK,"",codeList);
    }

    @Override
    public Result updateCode(Code code) {
        codeDao.updateCode(code);
        return new Result(true,StatusCode.OK,"修改令号状态成功");
    }

    @Override
    public Result checkCode(Code code) {
        List<Code> codes = codeDao.findCodeByField(code);
        return new Result(true,StatusCode.OK,"",codes.size()>0);
    }

    @Override
    @Transactional
    public Result handleCodeIsZy(Code code) {
            codeDao.updateCode(code);
            //令号在研关闭时需要关闭该令号下的所有项目
        if(!code.getZy()){
            Project project = new Project();
            project.setParentId(code.getId());
            List<Project> projects = projectDao.findProjectByField(project);
            if(projects!=null&&projects.size()>0){
                for (Project project1: projects) {
                    project1.setZy(code.getZy());
                    projectDao.update(project1);
                }
            }
        }
        return new Result(true,StatusCode.OK,"修改令号是否在研成功");
    }

    @Override
    public Result findCodeById(long id) {
        Code code = codeDao.findOne(id);
        return new Result(true,StatusCode.OK,"查询成功",code);
    }


    //confirm方法
    @Transactional
    public void commit(Map params){
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        logger.info("apply confirm begin 开始执行...xid:{}",transId);
        if(hmilyDao.isExistConfirm(transId)>0){
            logger.info("apply confirm 已经执行，无需重复执行...xid:{}",transId);
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
        logger.info("apply cancel begin 开始执行...xid:{}",transId);
        //	cancel幂等校验
        if(hmilyDao.isExistCancel(transId)>0){
            logger.info("apply cancel 已经执行，无需重复执行,xid:{}",transId);
            return;
        }
        //cancel空回滚处理，如果try没有执行，cancel不允许执行
        if(hmilyDao.isExistTry(transId)<=0){
            logger.info("apply空回滚处理，try没有执行，不允许cancel执行,xid:{}",transId);
            return;
        }
        long businesskey=(long)params.get("businesskey");
        Code code = new Code();
        code.setId(businesskey);
        code.setApply(false);
        codeDao.updateCode(code);
        hmilyDao.addCancel(transId);
        logger.info("apply cancel begin 执行完毕...xid:{}",transId);
    }

    @Override
    public Result findVisableUser(Long codeId) {
        List<String> userList = codeVisitableDao.findUsernamesByCodeId(codeId);
        return new Result(true,StatusCode.OK,"",userList);
    }

    @Override
    public Result checkRole(long id) {
        Code code = codeDao.findOne(id);
        /*** 令号的创建者*/
        String createBy = code.getCreateBy();
        /*** 令号的组长*/
        String header = code.getHeader();
        /*** 令号的总体负责人*/
        String leader = code.getLeader();
        /*** 令号的可访问权限组*/
        List<String> usernames = codeVisitableDao.findUsernamesByCodeId(id);
        /*** 登录用户名*/
        String username = LoginUserUtil.getLoginSysUser().getUsername();
        if (username.equals(createBy)||username.equals(header)||username.equals(leader)||usernames.contains(username)){
            return new Result(true,StatusCode.OK,"Ok",true);
        }
        return new Result(true,StatusCode.OK,"Ok",false);
    }

    @Override
    public Result countOfMonth(Map map) {
        Integer[] codeArray = new Integer[12];
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
            if(map.get("parentId")!=null&&!"".equals(map.get("parentId"))){
                queryMap.put("parentId",map.get("parentId"));
            }
            queryMap.put("startDate",start);
            queryMap.put("endDate",end);
            codeArray[i] = codeDao.fuzzyQueryList(queryMap).size();
        }
        return new Result(true, StatusCode.OK, "", codeArray);

    }
}

