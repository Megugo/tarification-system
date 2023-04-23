package com.alkafol.srmmicroservice.security;

import com.alkafol.srmmicroservice.models.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/manager/register", "/abonent/register", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
                    auth.requestMatchers("/manager/**").hasAuthority(Role.MANAGER.name());
                    auth.requestMatchers("/abonent/**").hasAuthority(Role.CLIENT.name());
                })
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }
}
