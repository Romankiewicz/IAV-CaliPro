package de.iav.backend.security;


import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public record AppUser(
        String id,
        @Indexed(unique = true, name = "_user_name_")
        String username,
        String password,
        @Indexed(unique = true, name = "_email_")
        String email,
        UserRole role
) {
}
