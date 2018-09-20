package com.InShowVideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;
 
@SpringBootApplication
@MapperScan(basePackages="com.InShowVideo.mapper")
@ComponentScan(basePackages= {"com.InShowVideo","org.n3r.idworker"})
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
