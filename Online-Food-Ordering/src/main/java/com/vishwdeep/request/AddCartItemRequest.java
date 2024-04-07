package com.vishwdeep.request;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemRequest {
    private  Long foodId;
    private Long quantity;
    private List<String> ingredients;
}
