package com.hotspice.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Geetanshu on 27/08/16.
 */
@Table(name = "order_detail")
@Entity
public class OrderDetail extends BaseEntity {

    @Column(name="user_mobile")
    private Long userMobile;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="order_amount")
    private Double orderAmount;

    @Column(name="discount_amount")
    private Double discountAmount;

    @Column(name="payment_amount")
    private Double paymentAmount;

    @Column(name="total_refund_amount")
    private Double totalRefundAmount;

    @Column(name="order_status")
    private String orderStatus;

    @Column(name="address")
    private String Address;

    @Column(name="delivered_by")
    private String deliveredBy;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="orderDetail", fetch=FetchType.EAGER)
    private List<CartDetail> cartDetails;

    public Long getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(Long userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(Double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }

    @PrePersist
    void prePersist() {
        this.setCreatedDate(new Date());
        this.setUpdatedDate(new Date());
    }

    @PreUpdate
    void preUpdate() {
        this.setUpdatedDate(new Date());
    }
}

