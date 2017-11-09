package com.example.demo.repository;

import com.example.demo.bean.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by guiqi on 2017/11/9.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByUsername(String username);

}
