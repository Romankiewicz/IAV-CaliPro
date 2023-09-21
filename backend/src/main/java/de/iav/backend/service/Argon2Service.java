package de.iav.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Argon2Service {

    Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(16, 32, 1, 1 << 12, 3);
    public String encode(String password){
        return argon2PasswordEncoder.encode(password);
    }


}
