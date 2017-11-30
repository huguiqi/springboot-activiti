package com.example.demo;

import com.example.demo.bean.Person;
import com.example.demo.repository.PersonRepository;
import org.activiti.engine.*;
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
public class JobTest {




    @Autowired
    PersonRepository personRepository;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;





    @Test
    public void testDeployProcessEngine(){

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("processes/JobTest-process.bpmn20.xml")
                .name("offlineDeployment")
                .deploy();

        System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
    }





    @Test
    public void testComplete(){

        List<Task> tasks = taskService.createTaskQuery().processInstanceId("1270001").list();
        Assert.notEmpty(tasks,"tasks不为空");

    }


    @Test
    public void testJobStart(){


        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("JobTaskProcess");

        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
        System.out.println("processId:"+processInstance.getProcessInstanceId());
        Assert.notEmpty(tasks,"tasks 不应该是空");
        for (Task task:tasks){
            System.out.println("taskId:"+task.getId()+"taskName:"+task.getName());
        }

    }



    @Test
    public void testExcetionList(){

       ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey("87050").active().singleResult();

        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
        System.out.println("processId:"+processInstance.getProcessInstanceId());
        Assert.notEmpty(tasks,"tasks 不应该是空");
        for (Task task:tasks){
            System.out.println("taskId:"+task.getId()+",taskName:"+task.getName());
        }

    }


    @Test
    public void testExcetionComplete(){

            taskService.complete("1247512");

    }

    @Test
    @Transactional
    public void testSavePerson(){
        Person person = new Person("test", "last", "first", new Date());
        personRepository.save(person);
        Assert.notNull(person.getId(),"is not null");
    }
}
