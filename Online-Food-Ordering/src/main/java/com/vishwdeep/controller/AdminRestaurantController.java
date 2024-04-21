package com.vishwdeep.controller;

import com.vishwdeep.model.Restaurant;
import com.vishwdeep.model.User;
import com.vishwdeep.request.CreateRestaurantRequest;
import com.vishwdeep.response.MessageResponse;
import com.vishwdeep.service.RestaurantService;
import com.vishwdeep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")

public class AdminRestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        System.out.println("Inside post ");
        User user = userService.findUserByJwtToken(jwt);
        System.out.println(user);
        Restaurant restaurant = restaurantService.createRestaurant(req, user);
        System.err.printf(user.toString());
        System.err.println(restaurant.toString());
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest req, @RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurant(id,req);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant( @RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        restaurantService.deleteRestaurant(id);
        MessageResponse res= new MessageResponse();
        res.setMessage("Restaurant Deleted Successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@RequestBody CreateRestaurantRequest req, @RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
        MessageResponse res= new MessageResponse();
        res.setMessage("Restaurant Status Updated Successfully");
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<Restaurant> FindRestaurantByUserId( @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId((long) user.getId());
        MessageResponse res= new MessageResponse();
        res.setMessage("Restaurant Status Updated Successfully");
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
