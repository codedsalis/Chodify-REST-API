package com.codedsalis.chowdify.shared.advice;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());

            errorDetail.setProperty("access_denied_reason", "Authentication failure");
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());

            errorDetail.setProperty("access_denied_reason", "Unauthorized");
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());

            errorDetail.setProperty("access_denied_reason", "JWT signature not valid");
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());

            errorDetail.setProperty("access_denied_reason", "JWT token has expired");
        }

        return errorDetail;
    }
}
