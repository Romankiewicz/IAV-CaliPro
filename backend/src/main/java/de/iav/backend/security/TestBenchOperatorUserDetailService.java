package de.iav.backend.security;

import de.iav.backend.exceptions.TestBenchOperatorUsernameNotFoundExeption;
import de.iav.backend.repository.TestBenchOperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestBenchOperatorUserDetailService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final TestBenchOperatorRepository testBenchOperatorRepository;

    public TestBenchOperatorUserDetailService(TestBenchOperatorRepository testBenchOperatorRepository) {
        this.testBenchOperatorRepository = testBenchOperatorRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return testBenchOperatorRepository.findTestBenchOperatorByUsername(username)
                .map(foundUser -> new User(foundUser.username(), foundUser.password(), List.of()))
                .orElseThrow(TestBenchOperatorUsernameNotFoundExeption::new);
    }
}

