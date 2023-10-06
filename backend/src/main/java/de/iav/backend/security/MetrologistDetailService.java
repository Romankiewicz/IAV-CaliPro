package de.iav.backend.security;

import de.iav.backend.exceptions.MetrologistUsernameNotFoundException;
import de.iav.backend.repository.MetrologistRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetrologistDetailService implements UserDetailsService {

    private final MetrologistRepository metrologistRepository;


    public MetrologistDetailService(MetrologistRepository metrologistRepository) {
        this.metrologistRepository = metrologistRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return metrologistRepository.findMetologistByUsername(username)
                 .map(foundUser -> new User(foundUser.username(), foundUser.password(), List.of()))
                .orElseThrow(MetrologistUsernameNotFoundException::new);

    }
}


