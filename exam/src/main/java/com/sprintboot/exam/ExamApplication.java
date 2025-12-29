package com.sprintboot.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamApplication.class, args);
		// var orderService = new OrderService(new StripePaymentService());
		// orderService.placeOrder();
	}

}
