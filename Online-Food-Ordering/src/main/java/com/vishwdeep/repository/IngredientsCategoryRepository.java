package com.vishwdeep.repository;

import com.vishwdeep.model.IngredientsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsCategoryRepository extends JpaRepository<IngredientsCategory,Long> {
    List<IngredientsCategory> findByRestaurantId(Long id);
}
