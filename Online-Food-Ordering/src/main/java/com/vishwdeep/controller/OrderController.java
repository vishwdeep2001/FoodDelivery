package com.vishwdeep.controller;

import com.vishwdeep.model.CartItem;
import com.vishwdeep.model.Order;
import com.vishwdeep.model.User;
import com.vishwdeep.request.AddCartItemRequest;
import com.vishwdeep.request.OrderRequest;
import com.vishwdeep.service.OrderService;
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
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(req,user);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory( @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        List<Order> order = orderService.getUsersOrder((long) user.getId());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
