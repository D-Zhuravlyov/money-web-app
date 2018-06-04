package com.money.account.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MoneyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyAppApplication.class, args);
	}
}
