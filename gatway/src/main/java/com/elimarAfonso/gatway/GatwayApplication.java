package com.elimarAfonso.gatway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatwayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatwayApplication.class, args);
	}

}
