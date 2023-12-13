package com.codedsalis.chowdify.food.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateFoodRequest(

        @Valid

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
        String name,
        @NotBlank(message = "Price cannot be empty")
        @Positive(message = "Price must be greater than 0")
        String price,
        String category
) { };
