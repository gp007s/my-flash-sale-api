package edu.umass.flashsale.service;

import edu.umass.flashsale.database.OrderfulfillmentRepository;
import edu.umass.flashsale.model.OrderfulfillmentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderFulfillmentService {

    @Autowired
    OrderfulfillmentRepository orderfulfillmentRepository;
    @Autowired
    RedisService redisService;

    @Async
    public CompletableFuture<Boolean> processOrder(OrderfulfillmentData orderfulfillmentData) {
       boolean fulfillmentBackendSuccess;
       try{
           orderfulfillmentData.setFulfillmentStatus("COMPLETED");
            orderfulfillmentRepository.save(orderfulfillmentData);
           fulfillmentBackendSuccess = true;
       } catch (Exception exp){
           String redisKey = "stock:"+orderfulfillmentData.getItemDetails();
           redisService.decrement(redisKey,orderfulfillmentData.getQuantity());
           System.out.println("DB Exception : "+ exp.getMessage());
           fulfillmentBackendSuccess= false;
       }
        System.out.println("SQL Server Insertion Success : "+ fulfillmentBackendSuccess);
        return CompletableFuture.completedFuture(fulfillmentBackendSuccess);
    }


}
