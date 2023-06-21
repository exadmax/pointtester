package com.exadmax.pointtester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PointtesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PointtesterApplication.class, args);
		Mover.main(args);
	}

}
