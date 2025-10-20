package edu.umass.flashsale.messaging;

import edu.umass.flashsale.config.MessagingConfig;
import edu.umass.flashsale.model.OrderfulfillmentData;
import edu.umass.flashsale.model.PurchaseRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RabbitMqMessageSender {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderForFulfillment(PurchaseRequest purchaseRequest, String orderId) {
        System.out.println("Trying to send the message to Rabbit MQ messaging Svc!! ");
        rabbitTemplate.convertAndSend(
                MessagingConfig.EXCHANGE,
                MessagingConfig.ROUTING_KEY,
                createFulfillmentDbData(purchaseRequest,orderId)
        );
        System.out.println("Rabbit MQ sent the message successfully !!!");
    }

    private OrderfulfillmentData createFulfillmentDbData(PurchaseRequest purchaseRequest, String orderId) {

        OrderfulfillmentData orderfulfillmentData =  new
                OrderfulfillmentData(orderId,
                purchaseRequest.getItemName()+String.valueOf(new Random().nextInt(100)),
                purchaseRequest.getItemName(),
                purchaseRequest.getQuantity(),
                purchaseRequest.getUserName(),
                purchaseRequest.getEmailAddress(),
                purchaseRequest.getPaymentAmount(),
                "PENDING"
        );
        return  orderfulfillmentData;
    }
}
