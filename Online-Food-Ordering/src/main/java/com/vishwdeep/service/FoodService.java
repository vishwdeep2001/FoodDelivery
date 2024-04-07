package com.vishwdeep.service;

import com.vishwdeep.model.Category;
import com.vishwdeep.model.Food;
import com.vishwdeep.model.Restaurant;
import com.vishwdeep.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal,String foodCategory);

    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId) throws Exception;
    public Food updateAvailablityStatus(Long foodId) throws Exception;
}
