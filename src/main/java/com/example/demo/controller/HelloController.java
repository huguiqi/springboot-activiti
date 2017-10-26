package com.example.demo.controller;

import com.example.demo.bean.Car;
import com.example.demo.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by guiqi on 2017/7/28.
 */

@RestController
public class HelloController {

    @Autowired
    private CarMapper carMapper;

    @RequestMapping("/")
    public String index() {

        Car car =  carMapper.search("别摸我",null).iterator().next();
        return "Greetings from Spring Boot!" + car;
    }


}
