package com.cravebite.backend_2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cravebite.backend_2.data.DataGenerator;

@SpringBootApplication
public class Backend2Application {

	public static void main(String[] args) {
		SpringApplication.run(Backend2Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(DataGenerator dataGenerator) {
		return args -> {
			dataGenerator.generateData();
			System.out.println("Data generated!");
		};
	}
}
