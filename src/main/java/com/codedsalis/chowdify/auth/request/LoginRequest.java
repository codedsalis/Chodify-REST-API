package com.codedsalis.chowdify.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @Email(message = "must be an email address")
    @NotBlank(message = "Email cannot be empty")
    @NotNull
    private String email;

    @NotNull
    @NotBlank
    private String password;
}
