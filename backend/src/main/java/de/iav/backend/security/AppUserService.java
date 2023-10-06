package de.iav.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = appUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(user.username())
                .password(user.password())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.role().name())))
                .build();
    }

    public AppUserResponse registerNewMetrologist(NewAppUser newAppUser){

        Argon2PasswordEncoder passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

        AppUser appUser = new AppUser(
                null,
                newAppUser.username(),
                passwordEncoder.encode(newAppUser.password()),
                newAppUser.email(),
                UserRole.Metrologist
        );
        AppUser savedAppUser = appUserRepository.save(appUser);
        return new AppUserResponse(savedAppUser.id(), savedAppUser.username(), savedAppUser.email(), savedAppUser.role());
    }

    public AppUserResponse registerNewOperator(NewAppUser newAppUser){

        Argon2PasswordEncoder passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

        AppUser appUser = new AppUser(
                null,
                newAppUser.username(),
                passwordEncoder.encode(newAppUser.password()),
                newAppUser.email(),
                UserRole.Operator
        );
        AppUser savedAppUser = appUserRepository.save(appUser);
        return new AppUserResponse(savedAppUser.id(), savedAppUser.username(), savedAppUser.email(), savedAppUser.role());
    }
}
