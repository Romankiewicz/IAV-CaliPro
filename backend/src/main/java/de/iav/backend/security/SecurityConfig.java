package de.iav.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 1 << 12, 3);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(c -> {
                    c.requestMatchers("/api/metrologist/register").permitAll();
                    c.requestMatchers("/api/operators/register").permitAll();
                    c.requestMatchers(HttpMethod.PUT, "/api/**").permitAll();
                    c.requestMatchers(HttpMethod.POST, "/api/metrologist/login").permitAll();
                    c.requestMatchers(HttpMethod.POST, "/api/operators/login").permitAll();
                    c.requestMatchers(HttpMethod.DELETE, "/api/metrologist/").authenticated();
                    c.requestMatchers(HttpMethod.GET, "/api/metrologist/").permitAll();//.hasRole(UserRole.Metrologist.name());
                    c.requestMatchers(HttpMethod.GET, "/api/operators/").permitAll();//.hasRole(UserRole.Operator.name());
                    c.anyRequest().permitAll();
                })
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(Customizer.withDefaults())
                .build();
    }

}



