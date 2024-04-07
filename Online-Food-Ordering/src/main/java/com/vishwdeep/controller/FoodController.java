package com.vishwdeep.controller;

import com.vishwdeep.model.Category;
import com.vishwdeep.model.Food;
import com.vishwdeep.model.Restaurant;
import com.vishwdeep.model.User;
import com.vishwdeep.request.CreateFoodRequest;
import com.vishwdeep.service.FoodService;
import com.vishwdeep.service.RestaurantService;
import com.vishwdeep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")

public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
       List<Food> food= foodService.searchFood(name);


        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Food>> getRestaurantFood(@PathVariable Long id,@RequestParam boolean vegetarian,
                                                        @RequestParam boolean seasonal,
                                                        @RequestParam boolean nonveg,
                                                        @RequestParam(required = false) String food_category,
                                                        @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getRestaurantsFood(id, vegetarian,nonveg,seasonal,food_category);


        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
