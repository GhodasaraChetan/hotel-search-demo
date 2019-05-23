package com.hotel.hotelsearchdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class HotelSearchDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelSearchDemoApplication.class, args);
	}

}
