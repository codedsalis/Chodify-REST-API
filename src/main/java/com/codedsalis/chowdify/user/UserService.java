package com.codedsalis.chowdify.user;

import com.codedsalis.chowdify.auth.request.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public  UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public User saveUser(UserRegistrationRequest request) {
//        User user = new User();
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        user.setPassword(request.getPassword());
//        this.userRepository.save(user);
//
//        return user;
//    }
}
