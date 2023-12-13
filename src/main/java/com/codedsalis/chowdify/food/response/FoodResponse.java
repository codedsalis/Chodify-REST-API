package com.codedsalis.chowdify.food.response;

import com.codedsalis.chowdify.food.Food;
import lombok.Data;

import java.util.List;

@Data
public class FoodResponse {
    private List<Food> foods;

    private String status;

    private Object data;

    private Food food;
}
