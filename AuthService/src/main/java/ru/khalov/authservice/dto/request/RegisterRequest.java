package ru.khalov.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.khalov.authservice.util.Role;

public record RegisterRequest(

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 5, max = 50, message = "Username should be from 5 to 50")
        String username,

        @NotBlank
        @Size(min = 8, max = 72, message = "Password should be from 8 to 72")
        String password,

        @NotNull
        Role role
) {
}
