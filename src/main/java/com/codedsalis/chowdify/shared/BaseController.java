package com.codedsalis.chowdify.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

public class BaseController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ChowdifyResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        HashMap<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        ChowdifyResponse chowdifyResponse = ChowdifyResponse.builder()
        .status("failed")
        .data(errors)
        .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(chowdifyResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ChowdifyResponse> handleGenericExceptions(Exception ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());

        ChowdifyResponse chowdifyResponse = ChowdifyResponse.builder()
        .status("failed")
        .data(errors)
        .build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(chowdifyResponse);
    }
}
