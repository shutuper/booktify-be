package org.qqsucc.booktify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BooktifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooktifyApplication.class, args);
	}

}
