package com.codedsalis.chowdify.food;

import com.codedsalis.chowdify.food.request.CreateFoodRequest;
import com.codedsalis.chowdify.food.response.FoodResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/food")
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public ResponseEntity<FoodResponse> getFoods() {
        List<Food> foods = foodService.getFoods();

        FoodResponse foodResponse = new FoodResponse();
        foodResponse.setFoods(foods);

        return ResponseEntity.status(HttpStatus.OK).body(foodResponse);
    }

    @PostMapping
    public ResponseEntity<FoodResponse> storeFood(@Valid @RequestBody CreateFoodRequest request) {
        Food createdFood = this.foodService.saveFood(request);

        FoodResponse foodResponse = new FoodResponse();
        foodResponse.setFood(createdFood);

        return ResponseEntity.status(HttpStatus.CREATED).body(foodResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
