package com.zzcx.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//扫描mybatis mapper包路径
@MapperScan("com.zzcx.springboot.mapper")
//扫描所有需要扫描的包，包括一些自用的工具类包所在的路径
@ComponentScan(basePackages={"com.zzcx.springboot","org.n3r.idworker"})
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
