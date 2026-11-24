package ru.khalov.authservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.khalov.authservice.repository.UserRepository;
import ru.khalov.authservice.dto.request.LoginRequest;
import ru.khalov.authservice.dto.request.RegisterRequest;
import ru.khalov.authservice.dto.response.TokenResponse;
import ru.khalov.authservice.entity.User;


@Service
public class AuthService {

    private final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService (UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional
    public TokenResponse reg(RegisterRequest req){
        String email = req.email().trim().toLowerCase();
        String username = req.username().trim().toLowerCase();

        log.info("Start new registration, username: {}, email: {}", username, email);

        if(userRepository.existsByEmail(email) || userRepository.existsByUsername(username)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email or username already exists");
        }

        User user = userRepository.save(new User(email, username, req.password(), req.role()));
        log.info("Registration successful!");
        return new TokenResponse(tokenService.createToken(user.getId().toString(), user.getPublicId(), user.getRole().name()));
    }

    public TokenResponse login(LoginRequest req){
        User user = userRepository.findByUsername(req.username().trim().toLowerCase())
                .filter(u -> passwordEncoder.matches(req.password(), u.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        return new TokenResponse(tokenService.createToken(user.getId().toString(), user.getPublicId() ,user.getRole().name()));

    }
}
