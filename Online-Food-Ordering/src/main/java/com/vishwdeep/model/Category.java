package com.vishwdeep.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable
{       @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id ;

    private String name;

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

}
