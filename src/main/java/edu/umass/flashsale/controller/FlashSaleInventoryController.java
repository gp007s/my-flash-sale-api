package edu.umass.flashsale.controller;

import edu.umass.flashsale.messaging.RabbitMqMessageSender;
import edu.umass.flashsale.model.PurchaseRequest;
import edu.umass.flashsale.model.PurchaseResponse;
import edu.umass.flashsale.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;


@RestController
@RequestMapping("/api/user")
public class FlashSaleInventoryController {

    @Autowired
    RedisService redisService;
    @Autowired
    RabbitMqMessageSender messageSender;

    @PostMapping("/order")
    public @ResponseBody PurchaseResponse orderItem(@RequestBody PurchaseRequest purchaseRequest) {
        PurchaseResponse purchaseResponse = new PurchaseResponse();
        String itemName = purchaseRequest.getItemName();
        int maxItemQuantityOnSale = purchaseRequest.getQuantity();
        try{
            String redisKey = "stock:"+itemName;

            long result = redisService.checkItemAvailabilityAndReserve(redisKey, maxItemQuantityOnSale);
            if(result<1){
                purchaseResponse.setOrderNumber("OUT-OF-STOCK");
            } else {
                UUID uuid = UUID.randomUUID();
                String uuidAsString = uuid.toString();
                purchaseResponse.setOrderNumber("ORD"+uuidAsString);
                //ASYNC CALL to start the fulfillment
                messageSender.sendOrderForFulfillment(purchaseRequest, purchaseResponse.getOrderNumber());
                purchaseResponse.setOrderStatus("ORDER-COMPLETED");
                purchaseResponse.setFulfillmentStatus("PENDING");
            }
        } catch (Exception e){
            String errorDetails = e.getMessage();
            purchaseResponse.setError(errorDetails);
            purchaseResponse.setOrderStatus("ORDER-FAILED");
        }
       return purchaseResponse;
    }

    @GetMapping("/order")
    public long getOrderDetails(@RequestParam String orderId) {
        //TODO: Call to SQL Server
        //Details Should be FulfillmentStatus and Email or emailSent or not.
        return 100l;
    }
}