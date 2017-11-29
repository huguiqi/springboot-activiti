package com.example.demo.common;

import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by guiqi on 2017/11/16.
 */
@Component
public class ActivitiUtil {


    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ManagementService managementService;



    public void startProcess(String processId,String businessKey,Map<String,Object> variables){
    /**第二个参数是businesskey:业务工单的主键,作为区分，一般流程名+id，当然如果id为uuid则无所谓 */
        runtimeService.startProcessInstanceByKey(processId, businessKey, variables);
    }

    /**
     * 当前登录人登录系统以后要执行的任务(包含个人任务+组任务)
     */
    public List<Task> getTasksByAssignee(String userId){
        List<Task> assigneeTasks =  taskService
                .createTaskQuery()
                .taskAssignee(userId)//参与者，个人任务查询
                .orderByTaskCreateTime()
                .desc()
                .list();
        List<Task> candidateTasks = taskService
                .createTaskQuery()
                .taskCandidateUser(userId)//参与者，组任务查询
                .orderByTaskCreateTime()
                .desc()
                .list();
        assigneeTasks.addAll(candidateTasks);
        return assigneeTasks;
    }


    /**
     * 根据taskId查找 businessKey
     */
    public String getBusinessKeyByTaskId(String taskId){
        Task task = taskService
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();
        ProcessInstance pi = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();
        return pi.getBusinessKey();
    }


    public ProcessInstance getProcessBybusinessKey(String key){
        //这里用的是userId，将保证同一时间一个用户不会有多个流程，所以返回一个
        return runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(key).singleResult();
    }


    public void test(){

    }
}
