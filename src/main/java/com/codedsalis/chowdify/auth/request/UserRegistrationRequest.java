package com.codedsalis.chowdify.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    @Email(message = "must be an email address")
    @NotBlank(message = "Email cannot be empty")
    @NotNull
    private String email;

    @Size(min = 2, max = 255, message = "Name must be between the range of 2 and 255 characters")
    @NotBlank
    @NotNull
    private String name;

    @NotNull
    @NotBlank
    private String password;
}
