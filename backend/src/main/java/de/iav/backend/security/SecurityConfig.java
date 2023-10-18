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
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(c -> {
                    c.requestMatchers(HttpMethod.POST, "/api/metrologist/register").permitAll();
                    c.requestMatchers(HttpMethod.POST, "/api/metrologist").authenticated();
                    c.requestMatchers(HttpMethod.PUT, "/api/metrologist/**").authenticated();
                    c.requestMatchers(HttpMethod.GET, "/api/metrologist/**").authenticated();
                    c.requestMatchers(HttpMethod.DELETE, "/api/metrologist/**").hasRole(UserRole.METROLOGIST.name());

//                    c.requestMatchers(HttpMethod.POST, "/api/operator/register").permitAll();
                    c.requestMatchers(HttpMethod.POST, "/api/operator").authenticated();
                    c.requestMatchers(HttpMethod.PUT, "/api/operator/**").authenticated();
                    c.requestMatchers(HttpMethod.GET, "/api/operator/**").authenticated();
                    c.requestMatchers(HttpMethod.DELETE, "/api/operator/**").hasRole(UserRole.OPERATOR.name());

                    c.requestMatchers(HttpMethod.POST, "/api/metrology").hasRole(UserRole.METROLOGIST.name());
                    c.requestMatchers(HttpMethod.PUT, "/api/metrology/**").hasRole(UserRole.METROLOGIST.name());
                    c.requestMatchers(HttpMethod.GET, "/api/metrology/**").hasAnyRole("METROLOGIST", "OPERATOR");
                    c.requestMatchers(HttpMethod.DELETE, "/api/metrology/**").hasRole(UserRole.METROLOGIST.name());

                    c.requestMatchers(HttpMethod.POST, "/api/testbenches").hasRole(UserRole.METROLOGIST.name());
                    c.requestMatchers(HttpMethod.PUT, "/api/testbenches/**").authenticated();
                    c.requestMatchers(HttpMethod.GET, "/api/testbenches/**").authenticated();
                    c.requestMatchers(HttpMethod.DELETE, "/api/testbenches/**").hasRole(UserRole.METROLOGIST.name());

                    c.anyRequest().permitAll();
                })
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(Customizer.withDefaults())
                .build();
    }

}



