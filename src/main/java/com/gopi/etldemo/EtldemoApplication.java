package com.gopi.etldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.gopi.etldemo.model" })
@ComponentScan(basePackages = {"com.gopi.etldemo.service", "com.gopi.etldemo.service.impl" , "com.gopi.etldemo.controller", "com.gopi.etldemo.model", "com.gopi.etldemo.repositories" })
@EnableJpaRepositories(basePackages = {"com.gopi.etldemo.repositories" })
public class EtldemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtldemoApplication.class, args);
	}

}
