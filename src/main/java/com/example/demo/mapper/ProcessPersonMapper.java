package com.example.demo.mapper;

import com.example.demo.bean.ProcessPerson;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by guiqi on 2017/11/16.
 */

@Mapper
public interface ProcessPersonMapper {

    public void save(ProcessPerson processPerson);

}
