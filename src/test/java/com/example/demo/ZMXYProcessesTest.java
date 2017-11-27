package com.example.demo;

import com.example.demo.bean.Person;
import com.example.demo.bean.ProcessPerson;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProcessPersonRepository;
import com.sun.jmx.snmp.tasks.TaskServer;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guiqi on 2017/11/14.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZMXYProcessesTest {



    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProcessPersonRepository processPersonRepository;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    IdentityService identityService;





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
    public void testStartZMXYProcess(){
            Person person = personRepository.findByUsername("sam");
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("userId", person.getId());
            variables.put("userName", person.getUsername());
            variables.put("creditPoints", 550);
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("zhimaiXY_Process",""+person.getId(), variables);


            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
            for (Task task:tasks){
                    System.out.println("current taskName: " + task.getName());
            }

    }



    @Test
    public void testFindTaskByUserId(){

        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processDefinitionKey("zhimaiXY_Process").involvedUser("1").list();
        for(ProcessInstance instance:processInstances){
           List<Task> tasks =  taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).active().list();
            System.out.println("tasks:"+tasks.size());
        }
    }

@Test
public void testNotAuditTask(){

    //查看当前用户的流程，一对一
        ProcessInstance processInstance =  runtimeService.createProcessInstanceQuery().processDefinitionKey("zhimaiXY_Process").processInstanceBusinessKey("1").singleResult();
       Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).processInstanceBusinessKey("1").singleResult();
        System.out.println("task:"+task.getName());
}




}


