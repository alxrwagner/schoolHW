package ru.hogwarts.schoolHW;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class SchoolHwApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolHwApplication.class, args);
	}

}
