package com.example.demo.repository;

import com.example.demo.bean.Car;
import com.example.demo.bean.House;
import com.example.demo.mapper.CarMapper;
import com.example.demo.mapper.HouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by sam on 2017/7/30.
 */

@Repository
public class PersonDataRepository {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Transactional
    public void addCarAndHouse(boolean rollback){

        carMapper.insert(new Car("大众","1010",2010,null));
        houseMapper.insert(new House("万科","100","上海"));
        if (rollback){
            throw new RuntimeException("添加数据出错，回滚数据");
        }
    }

    public Map<String,Object> queryFor(){
        List<Object>  list1 = Arrays.asList(carMapper.selectAll());
        List<Object>  list2 = Arrays.asList(houseMapper.selectAll());
        Map map = new HashMap();
        map.put("car",list1);
        map.put("house",list2);
        return map;
    }
}
