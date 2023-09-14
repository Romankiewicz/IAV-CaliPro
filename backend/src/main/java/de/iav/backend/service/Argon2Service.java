package de.iav.backend.service;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Argon2Service {

    Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();
    public String encode(String password){
        return argon2PasswordEncoder.encode(password);
    }
}
