package com.example.demo;

import com.example.demo.repository.PersonDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class Demo1ApplicationTests {

	@Autowired
	private PersonDataRepository personDataRepository;

	@Test
	public void testAdd() {
		//应用插入的数据被回滚
		personDataRepository.addCarAndHouse(true);

	}

	@Test
	public void testQuery(){

		System.out.println("=======查询所有的");
		Object carObj = personDataRepository.queryFor().get("car");
		Object houseObj = personDataRepository.queryFor().get("house");

		List carList =  Arrays.asList(carObj);
		List houseList =  Arrays.asList(houseObj);
		carList.forEach(System.out::println);
		houseList.forEach(System.out::println);
	}

}
