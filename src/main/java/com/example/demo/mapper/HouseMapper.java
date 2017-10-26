package com.example.demo.mapper;

import com.example.demo.bean.Car;
import com.example.demo.bean.House;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;


/**
 * Created by sam on 2017/7/29.
 */

@Mapper
public interface HouseMapper {

    @Insert("insert into T_HOUSE(name,square,city) values(#{name},#{square},#{city})")
    void insert(House house);

    @Select("select * from T_HOUSE")
    Collection<House> selectAll();

}
