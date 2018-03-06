package com.springSportShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan( basePackages = {"com.springSportShop.entities"})
public class SpringSportShopApplication {

	public static void main(String[] args) {
                System.out.print("aeae");
		SpringApplication.run(SpringSportShopApplication.class, args);
	}
}

