package ru.ssau.kurs.api.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class DefaultSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService inMemoryUserDetailsManager(@Value("${security.logins}") List<String> logins,
            @Value("${security.passwords}") List<String> passwords, @Value("${security.roles}") List<String> roles) {
        List<UserDetails> users = new ArrayList<UserDetails>();
        int size = Math.min(logins.size(), Math.min(passwords.size(), roles.size()));
        for (int i = 0; i < size; i++) {
            users.add(
                    User.withUsername(logins.get(i))
                            .password(passwordEncoder().encode(passwords.get(i)))
                            .roles(roles.get(i))
                            .build());
        }
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> {
                    csrf.disable();
                })
                .authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers( "/api/**").authenticated()
                                .requestMatchers("/api/login").anonymous()
                                .anyRequest().anonymous())

                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
