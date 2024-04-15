package com.codedsalis.chowdify.auth;

import com.codedsalis.chowdify.auth.request.LoginRequest;
import com.codedsalis.chowdify.auth.request.RefreshTokenRequest;
import com.codedsalis.chowdify.auth.response.AuthenticationResponse;
import com.codedsalis.chowdify.shared.BaseController;
import com.codedsalis.chowdify.auth.request.UserRegistrationRequest;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController extends BaseController {

    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
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

    @PostMapping("/refresh-access-token")
    public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest request) throws Exception {
        return refreshTokenService.findByToken(request.token())
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                String accessToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(request.token())
                    .build();
            }).orElseThrow(() -> new Exception("Refresh token not found"));
    }
}
