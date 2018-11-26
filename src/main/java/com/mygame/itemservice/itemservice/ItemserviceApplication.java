package com.mygame.itemservice.itemservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.mygame.itemservice", "com.mygame.itemservice.repository"})
public class ItemserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(ItemserviceApplication.class, args);
	}
}
