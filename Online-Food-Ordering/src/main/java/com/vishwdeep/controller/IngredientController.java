package com.vishwdeep.controller;

import com.vishwdeep.model.IngredientsCategory;
import com.vishwdeep.model.IngredientsItem;
import com.vishwdeep.request.IngredientCategoryRequest;
import com.vishwdeep.request.IngredientRequest;
import com.vishwdeep.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")

public class IngredientController {
    @Autowired
    private IngredientsService ingredientsService;


    @PostMapping("/category")
    private ResponseEntity<IngredientsCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req) throws Exception {
        IngredientsCategory item = ingredientsService.createIngredientsCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }
    @PostMapping("")
    private ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientRequest req) throws Exception {
        IngredientsItem item = ingredientsService.createIngredientItem(req.getRestaurantId(),req.getName(),req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}/stock")
    private ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable Long id) throws Exception {
      IngredientsItem item = ingredientsService.updateStock(id);
      return new ResponseEntity<>(item,HttpStatus.OK);
    }
    @GetMapping("/restaurant/{id}")
    private ResponseEntity<List<IngredientsItem> > getRestaurantIngredient(@PathVariable Long id) throws Exception {
        List<IngredientsItem> items = ingredientsService.findRestaurantsIngredients(id);

        return new ResponseEntity<>(items,HttpStatus.OK);
    }
    @GetMapping("/restaurant/{id}/category")
    private ResponseEntity<List<IngredientsCategory> > getRestaurantIngredientCategory(@PathVariable Long id) throws Exception {
        List<IngredientsCategory> items = ingredientsService.findIngredientsCategoryByRestaurantId(id);

        return new ResponseEntity<>(items,HttpStatus.OK);
    }


}
