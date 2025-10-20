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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(String itemDetails) {
        this.itemDetails = itemDetails;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getFulfillmentStatus() {
        return fulfillmentStatus;
    }

    public void setFulfillmentStatus(String fulfillmentStatus) {
        this.fulfillmentStatus = fulfillmentStatus;
    }
}
