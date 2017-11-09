package com.example.demo;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guiqi on 2017/10/27.
 */

@Ignore
public class VacationTest {

    @Before
    public void init(){
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:activiti;DB_CLOSE_DELAY=1000")
                .setJdbcUsername("sa")
                .setJdbcPassword("")
                .setJdbcDriver("org.h2.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        ProcessEngine processEngine = cfg.buildProcessEngine();
        String pName = processEngine.getName();
        String ver = ProcessEngine.VERSION;
        System.out.println("ProcessEngine [" + pName + "] Version: [" + ver + "]");
    }

    @Test
    public void testDeployProcessEngine(){

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("processes/VacationTest.bpmn")
                .deploy();

        System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
    }

    @Test
    public void testRequestVocation(){

        testDeployProcessEngine();

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("userName", "狗蛋");
        variables.put("numberOfDays", new Integer(3));
        variables.put("vacationMotivation", "老子累了，老子要休息几天");
        variables.put("startDate",new Date());
        variables.put("endDate",new Date());

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("VocationProcess", variables);
// Verify that we started a new process instance
        System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
        System.out.println("currentInstance:"+processInstance.getName());
    }


    @Test
    public void testListTask(){
        testRequestVocation();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
//        List<Task> tasks = taskService.createTaskQuery().list();
        //condidateGroup和assignee是互斥的

        // 查询出任务接受人组为management的任务列表
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
        for (Task task : tasks) {
            System.out.println("Task Id: " + task.getId());
            System.out.println("Task available: " + task.getName());
        }
    }




    @Test
    public void testCompleteTask(){
        testRequestVocation();

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("requestApproved", "NO");
        taskVariables.put("headManMotivation", "项目很紧，给老子加班!!");
        taskVariables.put("resend","YES");


        for (Task task : tasks) {
            System.out.println("Task Id: " + task.getId());
            System.out.println("Task available: " + task.getName());
            taskService.complete(task.getId(), taskVariables);
        }

    }


    @Test
    public void testNextResendTask(){
        testCompleteTask();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().list();

        for (Task task :tasks){
                System.out.println("task name:"+task.getName());
                taskService.complete(task.getId());
        }
        List<Task> tasks2= taskService.createTaskQuery().list();
        for (Task task:tasks2){
            System.out.println("task name:"+task.getName());
        }

    }
}
