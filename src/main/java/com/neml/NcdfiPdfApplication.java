package com.neml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan("com.neml")
public class NcdfiPdfApplication {

	public static void main(String[] args) {
		SpringApplication.run(NcdfiPdfApplication.class, args);
	}

}
