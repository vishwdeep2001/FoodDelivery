package com.vishwdeep.service;

import com.vishwdeep.model.Category;
import com.vishwdeep.model.Food;
import com.vishwdeep.model.Restaurant;
import com.vishwdeep.repository.FoodRepository;
import com.vishwdeep.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService{
    @Autowired
    private FoodRepository foodRepository;


    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
       Food food = new Food();
       food.setFoodCategory(category);
       food.setRestaurant(restaurant);
       food.setDescription(req.getDescription());
       food.setImages(req.getImages());
       food.setName(req.getName());
       food.setPrice(req.getPrice());
       food.setIngredients(req.getIngredients());
       food.setSeasonal(req.isSeasonal());
       food.setVegetarian(req.isVegetarian());
       Food savedFood =foodRepository.save(food);
       restaurant.getFoods().add(savedFood);
       return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian,
                                         boolean isNonVeg, boolean isSeasonal, String foodCategory) {
        List<Food> foods= foodRepository.findByRestaurantId(restaurantId);
        if(isVegetarian){
            foods=filterByVegetarian(foods,isVegetarian);

        }
        if(isNonVeg){
            foods = filterByNonVeg(foods, isNonVeg);
        }
        if(isSeasonal){
            foods = filterBySeasonal(foods,isSeasonal);
        }
        if(foodCategory != null && !foodCategory.equals("")) {
            foods =filterByFoodCategory(foods,foodCategory);
        }
        return foods;

    }

    private List<Food> filterByFoodCategory(List<Food> foods, String foodCategory) {
   return  foods.stream().filter(food-> {if(food.getFoodCategory()!=null){
        return food.getFoodCategory().getName().equals(foodCategory);
    }
    return false;
    }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food->food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food ->food.isVegetarian()== false).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food ->food.isVegetarian() == isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
       return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if(optionalFood.isEmpty()){
            throw new Exception("food does not exists");
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailablityStatus(Long foodId) throws Exception {
       Food food = findFoodById(foodId);
       food.setAvailable(!food.isAvailable());
       return foodRepository.save(food);

    }
}
