package com.xpanxion.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoBackendApplication {
	// This allows Spring Boot to host itself via spring-boot:run, acting like a Tomcat server
	public static void main(String[] args) {
		SpringApplication.run(TodoBackendApplication.class, args);
	}
}
