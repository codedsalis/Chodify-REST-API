package com.codedsalis.chowdify.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @Email(message = "Email field must be a valid email address")
    @NotBlank(message = "Email cannot be empty")
    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull
    @NotBlank
    private String password;
}
