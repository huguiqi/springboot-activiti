package com.example.demo.repository;

import com.example.demo.bean.ProcessPerson;
import com.example.demo.mapper.ProcessPersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by guiqi on 2017/11/9.
 */

@Repository
public class ProcessPersonRepository {

    @Autowired
    private ProcessPersonMapper processPersonMapper;





    public void save(ProcessPerson processPerson) {
        processPersonMapper.save(processPerson);
    }
}
