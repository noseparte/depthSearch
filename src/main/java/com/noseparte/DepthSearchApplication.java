package com.noseparte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DepthSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepthSearchApplication.class, args);
	}

}

