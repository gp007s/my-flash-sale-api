package edu.umass.flashsale.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class OrderfulfillmentData implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String orderId;
    private String itemId;
    private String itemDetails;
    private int quantity;
    private String userName;
    private String emailAddress;
    private long paymentAmount;
    private String fulfillmentStatus;

    public OrderfulfillmentData() {
    }

    public OrderfulfillmentData(String orderId, String itemId, String itemDetails, int quantity, String userName, String emailAddress, long paymentAmount, String fulfillmentStatus) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemDetails = itemDetails;
        this.quantity = quantity;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.paymentAmount = paymentAmount;
        this.fulfillmentStatus = fulfillmentStatus;
    }
}
