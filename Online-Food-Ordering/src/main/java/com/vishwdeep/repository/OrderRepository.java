package com.vishwdeep.repository;

import com.vishwdeep.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {


    public List<Order> findByRestaurantId(Long RestaurantId);

    List<Order> findByCustomerId(Long userId);
}
