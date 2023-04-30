package com.roommanagement;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@MapperScan(basePackages = { "com.roommanagement" }, annotationClass = Mapper.class)
public class RoomManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomManagementApplication.class, args);
	}

}
