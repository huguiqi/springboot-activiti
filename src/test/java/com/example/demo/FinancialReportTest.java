package com.example.demo;

import com.example.demo.bean.Person;
import com.example.demo.repository.PersonRepository;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guiqi on 2017/11/14.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinancialReportTest {


    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;



    @Test
    public void testDeployProcessEngine(){

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("processes/unAuto/financialReport.bpmn20.xml")
                .name("unAutoProcessDeploy")
                .deploy();
        System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
    }


@Test
public void testProcessId(){

        List<ProcessInstance>  processInstances =runtimeService.createProcessInstanceQuery().processDefinitionKey("financialReport").list();
        Assert.notEmpty(processInstances,"不为空");

        for (ProcessInstance processInstance:processInstances){
            System.out.println("processInstanceId:"+processInstance.getProcessInstanceId());
        }
}


    @Test
    public void testStart(){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("financialReport");

        System.out.println("processInstance:"+processInstance.getProcessInstanceId());
    }

    @Test
    public void testNoneUserAssgine(){
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("kermit").list();
        Assert.isTrue(tasks.size()==0,"应该是空的");
    }

    @Test
    public void testHaveUserGroup(){
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
        Assert.notEmpty(tasks,"应该有列表");

        for (Task task : tasks) {
            System.out.println("Following task is available for accountancy group: " + task.getName());

            // claim it
            taskService.claim(task.getId(), "fozzie");
        }

        // Verify Fozzie can now retrieve the task
        tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
        for (Task task : tasks) {
            System.out.println("Task for fozzie: " + task.getName());

            // Complete the task
            taskService.complete(task.getId());
        }

        System.out.println("Number of tasks for fozzie: "
                + taskService.createTaskQuery().taskAssignee("fozzie").count());

        // Retrieve and claim the second task
        tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
        for (Task task : tasks) {
            System.out.println("Following task is available for management group: " + task.getName());
            taskService.claim(task.getId(), "kermit");
        }

        // Completing the second task ends the process
        for (Task task : tasks) {
            taskService.complete(task.getId());
        }

        // verify that the process is actually finished
//        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId("1302501").singleResult();
        System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
    }









    @Test
    public void testlaim(){
        //被认领的task
        taskService.claim("4076","110110");

    }

    @Test
    public void testClaimForUnClaim(){
        //        HashMap<String,Object> map = new HashMap<String,Object>();
//        map.put("userId","110110");
//        taskService.setAssignee("4076","110110");
//        taskService.addCandidateUser("4076","110111");
//        taskService.complete("4076");
        taskService.createTaskQuery();
    }


}
