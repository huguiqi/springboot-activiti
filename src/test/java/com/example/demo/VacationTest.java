package com.example.demo;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("VocationProcess", variables);
// Verify that we started a new process instance
        System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
        System.out.println("currentInstance:"+processInstance.getName());
    }


    @Test
    public void testListTask(){
        // Fetch all tasks for the management group
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
        for (Task task : tasks) {
            System.out.println("Task Id: " + task.getId());
            System.out.println("Task available: " + task.getName());
        }
    }




    @Test
    public void testCompleteTask(){
        // Fetch all tasks for the management group
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("vacationApproved", "false");
        taskVariables.put("managerMotivation", "项目很紧，给老子加班!!");


        for (Task task : tasks) {
            System.out.println("Task Id: " + task.getId());
            System.out.println("Task available: " + task.getName());
            taskService.complete(task.getId(), taskVariables);
        }
    }
}
