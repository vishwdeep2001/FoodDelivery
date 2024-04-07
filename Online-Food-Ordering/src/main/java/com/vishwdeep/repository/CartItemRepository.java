package com.vishwdeep.repository;

import com.vishwdeep.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long>
{

}
