package com.example.demo.service;

import com.example.demo.bean.Person;
import com.example.demo.common.ActivitiUtil;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProcessPersonRepository;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by guiqi on 2017/11/16.
 */
@Service("zhiMaiService")
public class ACTZhiMaiXYService {


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessPersonRepository processPersonRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ActivitiUtil activitiUtil;

    public void auditNoPass(){
        Person person = personRepository.findByUsername("sam");
        ProcessInstance processInstance = activitiUtil.getProcessBybusinessKey(""+person.getId());
        System.out.println("未通过审核");

    }


}
