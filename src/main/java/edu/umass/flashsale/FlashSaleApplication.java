package edu.umass.flashsale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FlashSaleApplication {

	@GetMapping("/message")
	public String getMessage(){
		System.out.println("#######Govind Prasad Sahu########");
		return "FlashSaleApi is healthy!!!";
	}
	@GetMapping("/admin")
	public String setTheQuantityOfItem(){
		System.out.println("#######Quality Set Now########");
		return "Quantity Set now!!!";
	}
	public static void main(String[] args) {
		SpringApplication.run(FlashSaleApplication.class, args);
	}

}
