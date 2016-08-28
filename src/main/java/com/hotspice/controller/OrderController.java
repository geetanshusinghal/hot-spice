package com.hotspice.controller;

import com.hotspice.entities.Category;
import com.hotspice.entities.OrderDetail;
import com.hotspice.objects.CategoryRequest;
import com.hotspice.objects.OrderRequest;
import com.hotspice.objects.Request;
import com.hotspice.objects.Response;
import com.hotspice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Geetanshu on 28/08/16.
 */
@RestController
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    @RequestMapping(path = "/createOrder" , method = RequestMethod.POST)
    public Response<OrderDetail> createOrder(@RequestBody Request<OrderRequest> request) {
        logger.info("Creating category: " + request);
        try {
            Response<OrderDetail> response = new Response<OrderDetail>();
            OrderDetail orderDetail = orderService.createOrder(request.getPayload());
            response.setPayload(orderDetail);
            response.setStatus("200");
            return response;
        }catch (Throwable th){
            logger.error("Exception occurred in creating order" + th);
            return createErrorResponse("500", th.getMessage());
        }
    }

    @RequestMapping(path = "/order/{orderId}" , method = RequestMethod.GET)
    public Response<OrderDetail> getOrder(@PathVariable Long orderId) {
        logger.info("Get order for id: " + orderId);
        try {
            Response<OrderDetail> response = new Response<OrderDetail>();
            OrderDetail orderDetail = orderService.getOrder(orderId);
            response.setPayload(orderDetail);
            response.setStatus("200");
            return response;
        }catch (Throwable th){
            logger.error("Exception occurred in getting order" + th);
            return createErrorResponse("500", th.getMessage());
        }
    }

    @RequestMapping(path = "/searchOrder" , method = RequestMethod.GET)
    public Response<List<OrderDetail>> searchOrder(@RequestParam(required=false) String status,
                                                   @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startDate,
                                                   @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  Date endDate) {
        logger.info("Searching order");
        try {
            Response<List<OrderDetail>> response = new Response<List<OrderDetail>>();
            List<OrderDetail> orderList = orderService.searchOrder(status, startDate, endDate);
            response.setPayload(orderList);
            response.setStatus("200");
            return response;
        }catch (Throwable th){
            logger.error("Exception occurred in getting order" + th);
            return createErrorResponse("500", th.getMessage());
        }
    }

    @RequestMapping(path = "/updateOrder" , method = RequestMethod.POST)
    public Response<OrderDetail> updateOrder(@RequestBody Request<OrderRequest> request) {
        logger.info("Update order : " + request);
        try {
            Response<OrderDetail> response = new Response<OrderDetail>();
            OrderDetail orderDetail = orderService.updateOrder(request.getPayload());
            response.setPayload(orderDetail);
            response.setStatus("200");
            return response;
        }catch (Throwable th){
            logger.error("Exception occurred in getting order" + th);
            return createErrorResponse("500", th.getMessage());
        }
    }

    private Response createErrorResponse(String respCode, String message) {
        Response errorResponse = new Response();
        errorResponse.setStatus(respCode);
        errorResponse.setMessage(message);
        return errorResponse;
    }
}
