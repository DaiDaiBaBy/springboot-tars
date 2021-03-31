package com.zhoufu.springboottars;

import com.qq.tars.spring.annotation.EnableTarsServer;
import com.qq.tars.spring.annotation.TarsHttpService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//通过注解@EnableTarsServer标识这是一个TARS服务，并开启服务相关功能
//在spring-boot中，需要通过注解开启tars服务相关功能
@EnableTarsServer
@TarsHttpService("httpObj")
public class SpringbootTarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTarsApplication.class, args);
	}

}
