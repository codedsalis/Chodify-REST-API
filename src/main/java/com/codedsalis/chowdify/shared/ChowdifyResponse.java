package com.codedsalis.chowdify.food.response;

import com.codedsalis.chowdify.food.Food;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FoodResponse {
    private String status;

    private Object data;
}
