package de.iav.frontend.security;

public record UserRequest(
        String username,
        String password
) {
}
