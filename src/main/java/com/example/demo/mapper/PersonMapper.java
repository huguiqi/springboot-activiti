package com.example.demo.mapper;

import com.example.demo.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by sam on 2017/11/12.
 */

@Mapper
public interface PersonMapper {

    @Select("SELECT * FROM PERSON WHERE USERNAME = #{userName}")
    Person findByUserName(@Param("userName") String username);

    @Select("SELECT * FROM PERSON")
    List<Person> findAll();


    @Insert("INSERT INTO PERSON(USERNAME,FIRSTNAME,LASTNAME,BIRTHDATE) values(#{username},#{firstName},#{lastName},#{birthDate})")
    @Options(useGeneratedKeys = true,keyProperty = "id", keyColumn = "ID")
    void save(Person person);

}
