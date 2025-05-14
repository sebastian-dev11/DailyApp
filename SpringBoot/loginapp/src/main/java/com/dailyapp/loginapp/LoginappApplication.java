package com.dailyapp.loginapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dailyapp.loginapp.services.IngresoService;

@SpringBootApplication(scanBasePackages = "com.dailyapp.loginapp")
public class LoginappApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginappApplication.class, args);
	}
	@Bean
public IngresoService ingresoService() {
    return new IngresoService();
}


}
