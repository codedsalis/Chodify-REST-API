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

        ChowdifyResponse chowdifyResponse = ChowdifyResponse.builder().status("success").data(foods).build();

        return ResponseEntity.status(HttpStatus.OK).body(chowdifyResponse);
    }

    @PostMapping
    public ResponseEntity<ChowdifyResponse> storeFood(@Valid @RequestBody CreateFoodRequest request) {
        Food createdFood = this.foodService.saveFood(request);

        ChowdifyResponse chowdifyResponse = ChowdifyResponse.builder().data(createdFood).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(chowdifyResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChowdifyResponse> getFood(@PathVariable Long id) {
        Optional<Food> food = foodService.findById(id);

        if (food.isEmpty()) {
            ChowdifyResponse chowdifyResponse = ChowdifyResponse.builder().status("failed").data("Food not found").build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(chowdifyResponse);
        }

        ChowdifyResponse chowdifyResponse = ChowdifyResponse.builder().status("success").data(food).build();
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
