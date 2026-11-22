package ru.khalov.authservice.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.khalov.authservice.dto.request.LoginRequest;
import ru.khalov.authservice.dto.request.RegisterRequest;
import ru.khalov.authservice.dto.response.TokenResponse;
import ru.khalov.authservice.dto.response.UserResponse;
import ru.khalov.authservice.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest req){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.reg(req));
    }

    @PostMapping("/api/login")
    public ResponseEntity<TokenResponse> login(LoginRequest req){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(req));
    }
}
