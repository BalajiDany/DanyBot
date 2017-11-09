package com.source.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Server {

	public static void startServer(String[] args) {
		SpringApplication.run(Server.class, args);
	}
}
