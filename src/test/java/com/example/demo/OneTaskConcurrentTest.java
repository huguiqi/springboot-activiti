package com.example.demo;

import com.example.demo.bean.Person;
import com.example.demo.repository.PersonRepository;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guiqi on 2017/11/14.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class OneTaskConcurrentTest {




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
                .addClasspathResource("processes/one-task-process.bpmn20.xml")
                .deploy();

        System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
    }


    @Test
    public void testConcurrentProcess(){

        for (int i=0;i<200000;i++){
            Person person = new Person("userNameTest"+i,"firstName"+i,"lastName"+i,new Date());
            personRepository.save(person);
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("person", person);
            runtimeService.startProcessInstanceByKey("oneTaskProcess",""+person.getId(), variables);

            System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
        }


    }


    @Test
    public void testComplete(){

      List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().active().list();
        for (ProcessInstance processInstance:processInstanceList){
          List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
          for (Task task:tasks){
              taskService.complete(task.getId());
          }
        }

    }


    @Test
    public void testExcetionStart(){

        Person person = new Person("testExcetionStart","testExcetionStart","testExcetionStart",new Date());
        personRepository.save(person);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("person", person);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("oneTaskProcess",""+person.getId(), variables);

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

    @Test
    @Transactional
    public void testSavePerson(){
        Person person = new Person("test", "last", "first", new Date());
        personRepository.save(person);
        Assert.notNull(person.getId(),"is not null");
    }
}
