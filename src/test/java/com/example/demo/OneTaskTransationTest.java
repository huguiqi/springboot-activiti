package com.example.demo;

import com.example.demo.bean.Person;
import com.example.demo.repository.PersonRepository;
import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guiqi on 2017/11/14.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class OneTaskTransationTest{


    @Autowired
    ApplicationContext applicationContext;


    @Autowired
    PersonRepository personRepository;

    @Autowired
    RuntimeService runtimeService;


    @Autowired
    DataSourceProperties dataSourceProperties;

    @Test
    public void testDataSource() throws Exception {
        // 获取配置的数据源
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        // 查看配置数据源信息
        System.out.println(dataSource);
        System.out.println(dataSource.getClass().getName());
        System.out.println(dataSourceProperties);
    }



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
    @Transactional
    public void testTransaction(){
//        testDeployProcessEngine();
        Person person = personRepository.findByUsername("sam2");

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("person", person);
        runtimeService.startProcessInstanceByKey("oneTaskProcess", variables);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("oneTaskProcess");
        System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
    }



    @Test
    @Transactional
    public void testSavePerson(){
        personRepository.save(new Person("test","last","first",new Date()));
    }
}
