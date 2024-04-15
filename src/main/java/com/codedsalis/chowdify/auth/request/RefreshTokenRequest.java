package com.codedsalis.chowdify.auth.request;

import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
    @Valid

    @NotBlank
    UUID token
) {
}
