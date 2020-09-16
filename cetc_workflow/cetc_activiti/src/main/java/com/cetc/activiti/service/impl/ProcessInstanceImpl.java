package com.cetc.activiti.service.impl;

import com.cetc.activiti.dao.HmilyDao;
import com.cetc.activiti.service.ProcessInstanceService;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ProcessInstanceImpl implements ProcessInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessInstanceService.class);
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private HmilyDao hmilyDao;


    @Override
    @Transactional
    @Hmily(confirmMethod="confirmMethod", cancelMethod="cancelMethod")
    public Result startProcessInstance(String processDefinitionKey, Long businessKey, Map<String,Object> variables) {
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        logger.info("activiti try begin 开始执行...xid:{}",transId);
        //幂等判断 判断local_try_log表中是否有try日志记录，如果有则不再执行
        if(hmilyDao.isExistTry(transId)>0){
            logger.info("activiti try 已经执行，无需重复执行,xid:{}",transId);
            return null ;
        }
        //try悬挂处理，如果cancel、confirm有一个已经执行了，try不再执行
        if(hmilyDao.isExistConfirm(transId)>0 || hmilyDao.isExistCancel(transId)>0){
            logger.info("activiti try悬挂处理  cancel或confirm已经执行，不允许执行try,xid:{}",transId);
            return null;
        }
        try {
            String startUser=(String)variables.get("start_user");
            String title =(String) variables.get("title");
            identityService.setAuthenticatedUserId(startUser);
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, String.valueOf(businessKey), variables);
            runtimeService.setProcessInstanceName(processInstance.getId(),title);
            List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
            if(taskList!=null&&taskList.size()>0){
                taskService.setOwner(taskList.get(0).getId(),startUser);
                taskService.complete(taskList.get(0).getId());
            }
            logger.info("activiti try begin 完成执行...xid:{}",transId);
            //插入try执行记录,用于幂等判断
            hmilyDao.addTry(transId);
            return new Result(true, StatusCode.OK,"开启流程实例成功",processInstance.getId());
        }catch (Exception e){
            logger.error("**********开启流程实例失败reason: "+e.getMessage()+"***********");
            e.printStackTrace();
            throw new RuntimeException("流程开启失败，回滚");
        }

    }

    @Transactional
    public void confirmMethod(String processDefinitionKey, Long businessKey, Map<String,Object> variables){
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        logger.info("apply confirm begin 开始执行...xid:{}",transId);
        if(hmilyDao.isExistConfirm(transId)>0){
            logger.info("activiti confirm 已经执行，无需重复执行...xid:{}",transId);
            return ;
        }
        hmilyDao.addConfirm(transId);
    }

    /**
     * 根据bussinesskey查询流程实例并删除
     * @param processDefinitionKey
     * @param businessKey
     * @param variables
     */
    @Transactional
    public void cancelMethod(String processDefinitionKey, Long businessKey, Map<String,Object> variables){
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        logger.info("apply confirm cancel开始执行...xid:{}",transId);
        //	cancel幂等校验
        if(hmilyDao.isExistCancel(transId)>0){
            logger.info("activiti cancel 已经执行，无需重复执行,xid:{}",transId);
            return;
        }
        //cancel空回滚处理，如果try没有执行，cancel不允许执行
        if(hmilyDao.isExistTry(transId)<=0){
            logger.info("activiti 空回滚处理，try没有执行，不允许cancel执行,xid:{}",transId);
            return;
        }
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(String.valueOf(businessKey)).list();
        if(list!=null&&list.size()>0){
            for (ProcessInstance processInstance: list) {
                runtimeService.deleteProcessInstance(processInstance.getId(),"分布式事务异常回滚");
                historyService.deleteHistoricProcessInstance(processInstance.getId());
            }

        }
       hmilyDao.addCancel(transId);
    }

    @Override
    public Result findAllProcessInstance(String processDefinitionKey) {
        try {
            List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).list();
            return new Result(true,StatusCode.OK,"查询所有流程实例成功",list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR, "查询所有流程实例失败");
        }

    }

    @Override
    public Result genereateProcessImage(String processInstanceId) {
        //获得流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = null;
        if(processInstance==null){
            //查询已经结束的流程实例
            HistoricProcessInstance processInstanceHistory = historyService.createHistoricProcessInstanceQuery()
                            .processInstanceId(processInstanceId).singleResult();
            if (processInstanceHistory == null) {
                return null;
            }else{
                processDefinitionId = processInstanceHistory.getProcessDefinitionId();
            }
        }else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }


        //使用宋体
        String fontName = "宋体";
        //获取BPMN模型对象
        BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
        //获取流程实例当前的节点，需要高亮显示
        List<String> currentActs = new ArrayList<>();
        if (processInstance != null) {
            currentActs = runtimeService.getActiveActivityIds(processInstance.getId());
        }
        InputStream inputStream = processEngine.getProcessEngineConfiguration()
                .getProcessDiagramGenerator()
                .generateDiagram(model, "png", currentActs, new ArrayList<String>(),
                        fontName, fontName, fontName, null, 1.0);

        try {
            //使用base64方式返回前端显示图片
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i;
            while ((i=inputStream.read())!=-1){
                byteArrayOutputStream.write(i);
            }
            BASE64Encoder encoder = new BASE64Encoder();
            String baseimage = "data:image/png;base64,"+ encoder.encode(byteArrayOutputStream.toByteArray());
            return new Result(true,StatusCode.OK,"",baseimage);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"查询进度失败");
        }


    }

    @Override
    public Result genereateProcessImageByBussinessKey(String processDefinitionKey, Long businessKey) {
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processDefinitionKey).processInstanceBusinessKey(String.valueOf(businessKey)).orderByProcessInstanceStartTime().desc().list();
        Result result=null;
        if(list!=null&&list.size()>0){
           result = genereateProcessImage(list.get(0).getId());
       }
        return result;
    }


}
