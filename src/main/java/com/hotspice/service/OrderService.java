package com.hotspice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotspice.dao.DishDao;
import com.hotspice.dao.OrderDetailDao;
import com.hotspice.entities.*;
import com.hotspice.objects.*;
import com.hotspice.redis.RedisHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Geetanshu on 28/08/16.
 */
@Service
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    DishDao dishDao;

    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    RedisHelper redisHelper;

    ObjectMapper om = new ObjectMapper();

    public OrderDetail createOrder(OrderRequest orderRequest) throws Exception {
        List<CartDetail> cartList = getCartList(orderRequest.getCart());
        if(cartList == null || cartList.isEmpty()) {
            logger.error("Empty cart");
            throw new Exception("Empty cart");
        }
        OrderDetail orderDetail = createOrderObject(orderRequest, cartList);
        orderDetailDao.createOrder(orderDetail);

        String dishObjectString = om.writeValueAsString(cartList.get(0).getDish());
        redisHelper.setPair("hotfix.recent.order.dish", dishObjectString);
        for(CartDetail cartDetail : cartList) {
            redisHelper.incrementScore("hotfix.max.order.dish", 1, String.valueOf(cartDetail.getDish().getId()));
        }

        return orderDetail;
    }

    public OrderDetail updateOrder(OrderRequest orderRequest) throws Exception {
        OrderDetail orderDetail = orderDetailDao.getOrder(orderRequest.getOrderId());
        if(orderDetail == null) {
            logger.error("Order Not found");
            throw new Exception("Order Not found");
        }
        orderDetail.setOrderStatus(orderRequest.getStatus());
        if(!StringUtils.isEmpty(orderRequest.getDeliveredBy())) {
            orderDetail.setDeliveredBy(orderRequest.getDeliveredBy());
        }

        orderDetailDao.updateOrder(orderDetail);
        return orderDetail;
    }

    public OrderDetail getOrder(Long orderId) throws Exception {
        OrderDetail orderDetail = orderDetailDao.getOrder(orderId);
        return orderDetail;
    }

    public List<OrderDetail> searchOrder(String status, Date startDate, Date endDate) throws Exception {
        List<OrderDetail> orderList = orderDetailDao.searchOrder(status, startDate, endDate);
        return orderList;
    }

    private OrderDetail createOrderObject(OrderRequest orderRequest, List<CartDetail> cartList) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setActive(true);
        orderDetail.setAddress(orderRequest.getAddress());
        orderDetail.setUserEmail(orderRequest.getUserEmail());
        orderDetail.setUserMobile(orderRequest.getUserMobile());


        Double totalOrderAmount = 0D;
        if(cartList != null) {
            for(CartDetail cartDetail : cartList) {
                totalOrderAmount += cartDetail.getTotalAmount();
                cartDetail.setOrderDetail(orderDetail);
            }
        }
        orderDetail.setDiscountAmount(0D);
        orderDetail.setOrderAmount(totalOrderAmount);
        orderDetail.setPaymentAmount(totalOrderAmount);
        orderDetail.setTotalRefundAmount(0D);
        orderDetail.setOrderStatus("PAYMENT_COMPLETED");
        orderDetail.setCartDetails(cartList);
        return orderDetail;
    }

    public List<CartDetail> getCartList(List<CartRequest> cartRequestList) {
        if(cartRequestList ==null || cartRequestList.isEmpty()) {
            return null;
        }

        List<CartDetail> cartList = new ArrayList<CartDetail>();
        for(CartRequest cartRequest : cartRequestList) {
            Dish dish = dishDao.getDishByName(cartRequest.getDishName());
            if(dish!=null) {
                CartDetail cartDetail = new CartDetail();
                cartDetail.setStatus("INIT");
                cartDetail.setDish(dish);
                cartDetail.setPrice(dish.getPrice());
                cartDetail.setQuantity(cartRequest.getQuantity());
                cartDetail.setTotalAmount(dish.getPrice() * cartRequest.getQuantity());
                cartDetail.setTotalRefund(0D);
                cartDetail.setActive(true);
                cartList.add(cartDetail);
            }
        }
        return cartList;
    }
}
