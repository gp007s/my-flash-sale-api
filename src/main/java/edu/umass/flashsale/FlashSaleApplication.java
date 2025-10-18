package edu.umass.flashsale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FlashSaleApplication {

	@GetMapping("/health")
	public String health(){
		System.out.println("FlashSaleApi is healthy!!!");
		return "FlashSaleApi is healthy!!!";
	}

	public static void main(String[] args) {
		SpringApplication.run(FlashSaleApplication.class, args);
	}
}
