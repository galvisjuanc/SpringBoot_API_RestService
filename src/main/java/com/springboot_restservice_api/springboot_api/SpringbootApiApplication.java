package com.springboot_restservice_api.springboot_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springboot_restservice_api.springboot_api.repository.LibraryRepository;

@SpringBootApplication
public class SpringbootApiApplication{

@Autowired
LibraryRepository libraryRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApiApplication.class, args);
	}

}
