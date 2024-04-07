package com.vishwdeep.request;

import com.vishwdeep.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    @Query("Select r From Restaurant r where lower(r.name) LIKE lower(concat('%',:query,'%')) " +
            "OR lower(r.cuisineType) LIKE lower(concat('%',:query,'%'))")
    List<Restaurant> findBySearchQuery(String query);
    Restaurant findOwnerById(Long userId);
}
