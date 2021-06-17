package com.example.ecolytiq;

import com.example.ecolytiq.helper.FileHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication //(exclude = SecurityAutoConfiguration.class)
public class TaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}

	@Bean
	public FileHelper getFileHelper(){
		return new FileHelper();
	}

}
