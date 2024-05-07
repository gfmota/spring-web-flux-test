package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = FeignConfig.class)
public class AsyncCallsTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncCallsTestApplication.class, args);
	}

}
