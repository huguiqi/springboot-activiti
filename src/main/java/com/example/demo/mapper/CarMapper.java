package com.example.demo.mapper;

import com.example.demo.bean.Car;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;


/**
 * Created by sam on 2017/7/29.
 */

@Mapper
public interface CarMapper {

    @Insert("insert into car(make,model,year) values(#{make},#{model},#{year})")
    void insert(Car car);

    @Select("select * from CAR")
    Collection<Car> selectAll();

    Collection<Car> search(@Param("make") String make, @Param("model") String model);
}
