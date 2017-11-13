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


    @Insert("INSERT INTO PERSON(ID,USERNAME,FIRSTNAME,LASTNAME,BIRTHDATE) values(#{id},#{username},#{firstName},#{lastName},#{birthDate})")
    @SelectKey(statement = "SELECT SEQ_PERSON.Nextval as ID from DUAL",resultType = Long.class,before = true,keyProperty = "id",keyColumn = "ID")
    void save(Person person);

}
