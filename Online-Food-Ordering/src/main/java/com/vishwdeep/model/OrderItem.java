package com.vishwdeep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Food food;

    private int quantity;

    private Long totalPrice;

    private List<String> ingredients;


    @Override
    public String toString() {
        return "OrderItem{id=" + id + ", food='" + food.getName() + "', quantity=" + quantity + ", totalPrice=" + totalPrice + "}";
    }
}
