package com.yyzc.agent.apmagentdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApmAgentDemoApplication {

	public static void main(String[] args) {
		System.out.println("======执行main========方法");
		SpringApplication.run(ApmAgentDemoApplication.class, args);
	}
}
