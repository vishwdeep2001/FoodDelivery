package com.vishwdeep.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vishwdeep.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JsonBackReference
    private User customer;
    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    private long totalAmount;

    private String orderStatus;

    private Date createdAt;
    @ManyToOne
    private Address deliveryAddress;

    @OneToMany
    private List<OrderItem> items;

    private int totalItem;

    //private Payment payment;
    private Long totalPrice;

    @Override
    public String toString() {
        return "Order{id=" + id + ", customer='" + customer.getFullName() + "', orderStatus='" + orderStatus + "', createdAt='" + createdAt + "'}";
    }
}
