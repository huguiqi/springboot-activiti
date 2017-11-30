package com.example.demo.service.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * Created by guiqi on 2017/11/30.
 */
public class MyJavaDelegate implements JavaDelegate {
    public void execute(DelegateExecution execution){
//        String var = (String) execution.getVariable("input");
//        var = var.toUpperCase();
//        execution.setVariable("input", var);

        System.out.println("delegate execute run");
        System.out.println("打印。。。。。。。");

    }
}
