package com.lollibondchat.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.lollibondchat")
public class LollibondCompanyServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LollibondCompanyServicesApplication.class, args);
	}
	
}
