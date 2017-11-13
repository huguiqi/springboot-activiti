package com.example.demo.service;

import com.example.demo.bean.Person;
import com.example.demo.repository.PersonRepository;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guiqi on 2017/11/9.
 */
@Service
@Transactional
public class MyService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PersonRepository personRepository;

    public void startProcess(String userName) {

        Person person = personRepository.findByUsername(userName);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("person", person);
        runtimeService.startProcessInstanceByKey("oneTaskProcess", variables);
    }

    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    public List<Task> getTasksByName(String name){

        return taskService.createTaskQuery().taskName(name).list();
    }

    public void createDemoUsers() {
        if (personRepository.findAll().size() == 0) {
            personRepository.save(new Person("sam", "hu", "G", new Date()));
            personRepository.save(new Person("sam2", "hu", "G", new Date()));
        }
    }

    public void complete(String taskId) {
        taskService.complete(taskId);
    }
}
