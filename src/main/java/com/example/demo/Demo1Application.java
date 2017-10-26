package com.example.demo;

import com.example.demo.bean.Car;
import com.example.demo.mapper.CarMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }

    @Bean
    CommandLineRunner demo(CarMapper carMapper){
    return args ->{

        List<Car> cars = Arrays.asList(
                new Car("劳斯莱斯", "ls-101", 2013, null),
                new Car("别摸我", "bmw-102", 2014, null),
                new Car("奔驰", "bc-111", 2012, null));

        cars.forEach(car -> {
            carMapper.insert(car);
            System.out.println(car.toString());
        });

        System.out.println("---------------selectAll");
        carMapper.selectAll().forEach(System.out:: println);
        System.out.println("---------------search");
        carMapper.search("别摸我",null).forEach(System.out::println);
    };

    }

}


