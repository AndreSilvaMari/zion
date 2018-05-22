package com.zion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ZionApplication {

//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
//		return application.sources(ZionApplication.class);
//	}
	public static void main(String[] args) {
		SpringApplication.run(ZionApplication.class, args);
	}


}
