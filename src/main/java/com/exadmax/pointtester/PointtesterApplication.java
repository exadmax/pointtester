package com.exadmax.pointtester;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class PointtesterApplication {

	public static void main(String[] args) {
		// Keep AWT enabled for optional micro-jiggle fallback.
		SpringApplicationBuilder builder = new SpringApplicationBuilder (PointtesterApplication.class);
		builder.headless (false).run (args);
	}
}
