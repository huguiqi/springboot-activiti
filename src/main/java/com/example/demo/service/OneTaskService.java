package com.example.demo.service;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.springframework.stereotype.Service;

/**
 * Created by guiqi on 2017/11/13.
 */

@Service
public class OneTaskService {

    public void printNewTask(){

        System.out.println("printNewTask");
//        throw new RuntimeException("打印操作出错");
    }
}
