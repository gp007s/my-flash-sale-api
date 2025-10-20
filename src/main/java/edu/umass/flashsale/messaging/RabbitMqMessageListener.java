package edu.umass.flashsale.messaging;

import edu.umass.flashsale.communication.CommunicationProcessor;
import edu.umass.flashsale.config.MessagingConfig;
import edu.umass.flashsale.model.OrderfulfillmentData;
import edu.umass.flashsale.service.OrderFulfillmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.concurrent.CompletableFuture;

@Service
public class RabbitMqMessageListener {

    @Autowired
    OrderFulfillmentService orderService;

    @Autowired
    CommunicationProcessor communicationProcessor;

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void receiveOrderForFulfillment(OrderfulfillmentData data) {
        System.out.println("Rabbit MQ received the message successfully !!! ");
        processOrderAndEmail(data);
    }
    private void processOrderAndEmail(OrderfulfillmentData orderfulfillmentData) {
        CompletableFuture<Boolean> future = orderService.processOrder(orderfulfillmentData);
        System.out.println("Processing the order fulfillment !!! ");
        // Process the result when it's available
        future.thenAccept(isFulfilled ->  communicationProcessor.sendEmailCommunication(isFulfilled, orderfulfillmentData.getOrderId(),
                orderfulfillmentData.getEmailAddress(), orderfulfillmentData.getItemDetails()));
    }
}
