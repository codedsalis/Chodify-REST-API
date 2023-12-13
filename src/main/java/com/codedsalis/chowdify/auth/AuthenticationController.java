package com.codedsalis.chowdify.auth;

import com.codedsalis.chowdify.auth.request.LoginRequest;
import com.codedsalis.chowdify.auth.response.AuthenticationResponse;
import com.codedsalis.chowdify.user.User;
import com.codedsalis.chowdify.user.UserService;
import com.codedsalis.chowdify.auth.request.UserRegistrationRequest;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationRequest request) throws BadRequestException {
        AuthenticationResponse response = this.authenticationService.register(request);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginRequest request) {
        AuthenticationResponse response = this.authenticationService.authenticate(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
