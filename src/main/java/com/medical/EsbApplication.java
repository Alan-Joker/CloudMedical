package com.medical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling   // 2.开启定时任务
public class EsbApplication {
	public static void main(String[] args) {
		SpringApplication.run(EsbApplication.class, args);

	}
}