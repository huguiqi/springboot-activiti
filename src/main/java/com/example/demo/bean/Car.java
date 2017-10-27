package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sam on 2017/7/29.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private String make, model;
    private int year;
    private Long id;

}
