package de.iav.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 1 << 12, 3);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
//                .authorizeHttpRequests(c -> {
//                    c.requestMatchers(HttpMethod.POST, "/api/metrologist/**").permitAll();
//                    c.requestMatchers(HttpMethod.GET, "/test").permitAll();
//                    c.anyRequest().permitAll();
//                })
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(AbstractHttpConfigurer::disable)
//                .logout(Customizer.withDefaults())
//                .build();
}


