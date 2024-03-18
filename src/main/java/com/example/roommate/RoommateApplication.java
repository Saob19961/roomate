package com.example.roommate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.example.roommate.domain.model.repository")
@EntityScan("com.example.roommate.domain.model")
@SpringBootApplication
public class RoommateApplication {
	public static void main(String[] args) {
		SpringApplication.run(RoommateApplication.class, args);
	}
}
