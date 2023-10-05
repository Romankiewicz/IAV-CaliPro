package de.iav.backend.security;

import de.iav.backend.exceptions.NoSuchUserException;
import de.iav.backend.repository.MetrologistRepository;
import de.iav.backend.repository.TestBenchOperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements AppUserRepository {

    private final MetrologistRepository metrologistRepository;
    private final TestBenchOperatorRepository testBenchOperatorRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override

    public UserDetails loadUserByUsername(String userName) throws NoSuchUserException {
        AppUser user = appUserRepository.findUserByUserName(userName)
                .orElseThrow(() -> new NoSuchUserException());
        return User.builder()
                .username(user.userName())
                .password(user.password())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.role().name())))
                .build();
    }
}
