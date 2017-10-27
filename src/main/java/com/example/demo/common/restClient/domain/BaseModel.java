package com.example.demo.common.restClient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by guiqi on 2017/8/25.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel {
    private Integer total;
    private Object object;


}