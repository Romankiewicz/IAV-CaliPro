package de.iav.backend.security;

import de.iav.backend.exceptions.MetrologistAlreadyExistException;
import de.iav.backend.exceptions.TestBenchOperatorAlreadyExistException;
import de.iav.backend.service.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final IdService idService;

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

    public AppUserResponse registerNewMetrologist(NewAppUser newAppUser) {

        Argon2PasswordEncoder passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

        if (appUserRepository.existsByUsername(newAppUser.username())) {
            throw new MetrologistAlreadyExistException();
        }

        AppUser appUser = new AppUser(
                idService.generateId(),
                newAppUser.username(),
                passwordEncoder.encode(newAppUser.password()),
                newAppUser.email(),
                UserRole.METROLOGIST
        );
        AppUser savedAppUser = appUserRepository.save(appUser);
        return new AppUserResponse(savedAppUser.id(), savedAppUser.username(), savedAppUser.email(), savedAppUser.role());
    }

    public AppUserResponse registerNewOperator(NewAppUser newAppUser) {

        Argon2PasswordEncoder passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

        if (appUserRepository.existsByUsername(newAppUser.username())) {
            throw new TestBenchOperatorAlreadyExistException();
        }

        AppUser appUser = new AppUser(
                idService.generateId(),
                newAppUser.username(),
                passwordEncoder.encode(newAppUser.password()),
                newAppUser.email(),
                UserRole.OPERATOR
        );
        AppUser savedAppUser = appUserRepository.save(appUser);
        return new AppUserResponse(savedAppUser.id(), savedAppUser.username(), savedAppUser.email(), savedAppUser.role());
    }
}
