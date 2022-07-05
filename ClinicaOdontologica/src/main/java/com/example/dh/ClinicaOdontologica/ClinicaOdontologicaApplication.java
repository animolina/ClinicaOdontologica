package com.example.dh.ClinicaOdontologica;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicaOdontologicaApplication {

	public static void main(String[] args) {
		PropertyConfigurator.configure("src/main/log4j.properties");
		SpringApplication.run(ClinicaOdontologicaApplication.class, args);
	}

}
