package com.codedsalis.chowdify.food;

import com.codedsalis.chowdify.food.request.CreateFoodRequest;
import com.codedsalis.chowdify.shared.ChowdifyResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/food")
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }


    @GetMapping
    public ResponseEntity<ChowdifyResponse> getAllFoods() {
        List<Food> foods = foodService.getFoods();

        HashMap<String, List<Food>> data = new HashMap<>();
        data.put("foods", foods);

        ChowdifyResponse chowdifyResponse = ChowdifyResponse.builder()
                .status("success")
                .data(data)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(chowdifyResponse);
    }

    @PostMapping
    public ResponseEntity<ChowdifyResponse> storeFood(@Valid @RequestBody CreateFoodRequest request) {
        Food createdFood = this.foodService.saveFood(request);

        HashMap<String, Food> data = new HashMap<>();
        data.put("food", createdFood);

        ChowdifyResponse chowdifyResponse = ChowdifyResponse.builder()
                .status("success")
                .data(data)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(chowdifyResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChowdifyResponse> getFood(@PathVariable Long id) {
        Optional<Food> food = foodService.findById(id);

        HashMap<String, Optional<Food>> foodData = new HashMap<>();
        foodData.put("food", food);

        ChowdifyResponse chowdifyResponse = ChowdifyResponse.builder()
                .status("success")
                .data(foodData)
                .build();

        if (food.isEmpty()) {
            HashMap<String, String> error = new HashMap<>();
            error.put("message", "Food not found");

            chowdifyResponse = ChowdifyResponse.builder()
                    .status("failed")
                    .data(error)
                    .build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(chowdifyResponse);
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
