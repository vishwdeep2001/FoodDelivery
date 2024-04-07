package com.vishwdeep.service;

import com.vishwdeep.model.IngredientsCategory;
import com.vishwdeep.model.IngredientsItem;
import com.vishwdeep.model.Restaurant;
import com.vishwdeep.repository.IngredientsCategoryRepository;
import com.vishwdeep.repository.IngredientsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientsService{
    @Autowired
    private IngredientsItemRepository ingredientsItemRepository;

    @Autowired
    private IngredientsCategoryRepository ingredientsCategoryRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientsCategory createIngredientsCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory category = new IngredientsCategory();
        category.setName(name);
        category.setRestaurant(restaurant);
        return ingredientsCategoryRepository.save(category);
    }

    @Override
    public IngredientsCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientsCategory> opt= ingredientsCategoryRepository.findById(id);
        if(opt.isEmpty()) {
            throw  new Exception("Ingredients category is not found");
        }
        return opt.get();
    }

    @Override
    public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientsCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredienName, Long CategoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory category = findIngredientCategoryById(CategoryId);
        IngredientsItem item = new IngredientsItem();
        item.setName(ingredienName);
        item.setRestaurant(restaurant);
        item.setCategory(category);
        IngredientsItem ingredientsItem = ingredientsItemRepository.save(item);
        category.getIngredients().add(ingredientsItem);
        return ingredientsItem;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {
        return ingredientsItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
       Optional<IngredientsItem> item = ingredientsItemRepository.findById(id);
       if(item.isEmpty())
           throw new Exception("Ingredient not found");
        IngredientsItem ingredientsItem = item.get();
        ingredientsItem.setInStock(!ingredientsItem.isInStock());
        return  ingredientsItemRepository.save(ingredientsItem);

    }

}
