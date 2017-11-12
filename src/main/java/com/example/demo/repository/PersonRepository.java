package com.example.demo.repository;

import com.example.demo.bean.Person;
import com.example.demo.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.channels.AsynchronousFileChannel;
import java.util.Collection;
import java.util.List;

/**
 * Created by guiqi on 2017/11/9.
 */

@Repository
public class PersonRepository  {

    @Autowired
    private PersonMapper personMapper;


    public Person findByUsername(String username){
        return personMapper.findByUserName(username);
    }



    public List<Person> findAll() {

        return personMapper.findAll();
    }

    public void save(Person person) {
        personMapper.save(person);
    }
}
