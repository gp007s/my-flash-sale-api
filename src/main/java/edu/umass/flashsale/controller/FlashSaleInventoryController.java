package edu.umass.flashsale.controller;

import edu.umass.flashsale.communication.CommunicationProcessor;
import edu.umass.flashsale.model.PurchaseRequest;
import edu.umass.flashsale.model.PurchaseResponse;
import edu.umass.flashsale.service.OrderService;
import edu.umass.flashsale.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.CompletableFuture;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;


@RestController
@RequestMapping("/api/user")
public class FlashSaleInventoryController {

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    CommunicationProcessor communicationProcessor;

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
                // AzureEventHubMessageSender azureEventHubMessageSender = new AzureEventHubMessageSender();
               // boolean fulfillDBStatus = orderService.processOrder(purchaseRequest,uuidAsString,fulfillmentStatus);
                //System.out.println("### Logging the fulfillmentStatus :" + fulfillDBStatus);
                processOrderAndEmail(purchaseRequest, uuidAsString, purchaseResponse);
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

    private void processOrderAndEmail(PurchaseRequest purchaseRequest, String uuidAsString, PurchaseResponse purchaseResponse) {
        CompletableFuture<Boolean> future = orderService.processOrder(purchaseRequest, uuidAsString,"COMPLETED");
        System.out.println("Processing the order fulfillment !!! ");
        // Process the result when it's available
        future.thenAccept(isFulfilled ->  communicationProcessor.sendEmailCommunication(isFulfilled, purchaseResponse.getOrderNumber(),
                purchaseRequest.getEmailAddress(), purchaseRequest.getItemName()));
    }

    @GetMapping("/order")
    public long getOrderDetails(@RequestParam String orderId) {
        //TODO: Call to SQL Server
        //Details Should be FulfillmentStatus and Email or emailSent or not.
        return 100l;
    }
}