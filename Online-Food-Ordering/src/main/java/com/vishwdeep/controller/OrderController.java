package com.vishwdeep.controller;

import com.vishwdeep.model.CartItem;
import com.vishwdeep.model.Order;
import com.vishwdeep.model.User;
import com.vishwdeep.request.AddCartItemRequest;
import com.vishwdeep.request.OrderRequest;
import com.vishwdeep.response.PaymentResponse;
import com.vishwdeep.service.OrderService;
import com.vishwdeep.service.PaymentService;
import com.vishwdeep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody OrderRequest req, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(req,user);
        PaymentResponse response = paymentService.createPaymentLink(order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory( @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        System.err.println(user.toString());
        List<Order> order = orderService.getUsersOrder((long) user.getId());
        System.err.println(order.toString());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
