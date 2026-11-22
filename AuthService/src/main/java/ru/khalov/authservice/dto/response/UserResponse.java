package ru.khalov.authservice.dto.response;

import ru.khalov.authservice.util.Role;

public record UserResponse(
        String username,
        Role role
) {
}
