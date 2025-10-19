package edu.umass.flashsale.service;

import edu.umass.flashsale.database.OrderfulfillmentRepository;
import edu.umass.flashsale.model.OrderfulfillmentData;
import edu.umass.flashsale.model.PurchaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OrderService {

    @Autowired
    OrderfulfillmentRepository orderfulfillmentRepository;

    @Async
    public boolean processOrder(PurchaseRequest purchaseRequest, String orderId, String fulfillmentStatus) {
       boolean fulfillmentBackendSuccess;
       try{
           OrderfulfillmentData orderfulfillmentData = orderfulfillmentRepository.save(createFulfillmentDbData(purchaseRequest,orderId,fulfillmentStatus));
           orderfulfillmentRepository.save(orderfulfillmentData);
           fulfillmentBackendSuccess = true;
       } catch (Exception exp){
           System.out.println("DB Exception : "+ exp.getMessage());
           fulfillmentBackendSuccess= false;
       }
        return fulfillmentBackendSuccess;
    }

    private OrderfulfillmentData createFulfillmentDbData(PurchaseRequest purchaseRequest, String orderId, String fulfillmentStatus) {

         HashMap<String, String> productMapping = new HashMap<>();
        productMapping.put("TV","Item1");
        productMapping.put("Refrigerator","Item2");
        productMapping.put("Samsung Phone","Item3");
        productMapping.put("Apple Phone","Item4");
        OrderfulfillmentData orderfulfillmentData =  new
                OrderfulfillmentData(orderId,
                productMapping.get(purchaseRequest.getItemName()),
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
