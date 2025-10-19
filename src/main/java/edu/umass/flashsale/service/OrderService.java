package edu.umass.flashsale.service;

import edu.umass.flashsale.database.OrderfulfillmentRepository;
import edu.umass.flashsale.model.OrderfulfillmentData;
import edu.umass.flashsale.model.PurchaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {

    @Autowired
    OrderfulfillmentRepository orderfulfillmentRepository;
    @Autowired
    RedisService redisService;

    @Async
    public CompletableFuture<Boolean> processOrder(PurchaseRequest purchaseRequest, String orderId, String fulfillmentStatus) {
       boolean fulfillmentBackendSuccess;
       try{
            orderfulfillmentRepository.save(createFulfillmentDbData(purchaseRequest,orderId,fulfillmentStatus));
           fulfillmentBackendSuccess = true;
       } catch (Exception exp){
           String redisKey = "stock:"+purchaseRequest.getItemName();
           redisService.decrement(redisKey,purchaseRequest.getQuantity());
           System.out.println("DB Exception : "+ exp.getMessage());
           fulfillmentBackendSuccess= false;
       }
        System.out.println("SQL Server Insertion Success : "+ fulfillmentBackendSuccess);
        return CompletableFuture.completedFuture(fulfillmentBackendSuccess);
    }

    private OrderfulfillmentData createFulfillmentDbData(PurchaseRequest purchaseRequest, String orderId, String fulfillmentStatus) {

        OrderfulfillmentData orderfulfillmentData =  new
                OrderfulfillmentData(orderId,
               purchaseRequest.getItemName()+String.valueOf(new Random().nextInt(100)),
                purchaseRequest.getItemName(),
                purchaseRequest.getQuantity(),
                purchaseRequest.getUserName(),
                purchaseRequest.getEmailAddress(),
                purchaseRequest.getPaymentAmount(),
                fulfillmentStatus
                );
        return  orderfulfillmentData;
    }
}
