package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by guiqi on 2017/11/16.
 */
@Data
@AllArgsConstructor
public class ProcessPerson {

    private Integer id;

    private Long userId;
    private String insProcessId;
    private String processStatus;//流程完成状态0为未完成，1为已完成


}
