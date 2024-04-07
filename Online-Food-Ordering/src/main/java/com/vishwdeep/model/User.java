package com.vishwdeep.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vishwdeep.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String fullName;

    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)// to restrict password for write only mode
    private String password;
    @JsonIgnore
    private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;

    @JsonIgnore// to ignore order list while fetching user
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")// inside order entity we have user model called customer
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    private List<RestaurantDto>favorites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)//so than when user deleted all his address also get deleted
    private List<Address> addresses = new ArrayList<>();
}
