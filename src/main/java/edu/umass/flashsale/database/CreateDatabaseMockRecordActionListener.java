package edu.umass.flashsale.database;

import edu.umass.flashsale.model.OrderfulfillmentData;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class CreateDatabaseMockRecordActionListener {
   @Bean
    ApplicationListener<ApplicationReadyEvent> basicsApplicationListener(OrderfulfillmentRepository repository) {

        return event->repository
                .saveAll(Stream.of("A", "B", "C").map(name -> new OrderfulfillmentData("order123455","item1","TV", 3,"Govind","gp007s@gmail.com", 200l,"FULFILLED")).collect(Collectors.toList()))
                .forEach(System.out::println);
    }

}
