package com.example.Back_End;

import com.example.Back_End.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketingApplication {

	private static Logger logger;

	@Autowired
	public TicketingApplication(Logger logger) {
		TicketingApplication.logger = logger;
	}

	public static void main(String[] args) {
		SpringApplication.run(TicketingApplication.class, args);
		logger.logInitialize();
		logger.log("Backend is starting...");
		logger.log("Backend started successfully.");
	}
}
