package com.codedsalis.chowdify.food;

import com.codedsalis.chowdify.food.request.CreateFoodRequest;
import com.codedsalis.chowdify.user.User;
import com.codedsalis.chowdify.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    @Autowired
    public FoodService(
            FoodRepository foodRepository,
            UserRepository userRepository
    ) {
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
    }

    public List<Food> getFoods() {
        return foodRepository.findAll();
    }

    public  Optional<Food> findById(Long foodId) {
         return foodRepository.findById(foodId);
    }

    public Food saveFood(CreateFoodRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            String userEmail = userDetails.getUsername();

            User user = userRepository.findByEmail(userEmail);

            if (user != null) {
                Food food = Food.builder()
                        .user(user)
                        .name(request.name())
                        .price(Integer.valueOf(request.price()))
                        .category(request.category())
                        .build();

                foodRepository.save(food);

                return food;
            } else {
                throw new RuntimeException("User not found for email: " + userEmail);
            }
        } else {
            throw new RuntimeException("Authentication principal is not an instance of UserDetails");
        }
    }
}
