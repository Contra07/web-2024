package ru.ssau.kurs.api.security;

import java.util.Base64;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserDetailsService securityService;
    private final PasswordEncoder encoder;
    public String authUser(Credential cred) throws IllegalArgumentException{
        if(cred == null )
            throw new IllegalArgumentException("empty credential");
        if(cred.getUsername() == null || cred.getUsername().isEmpty()) {
            throw new IllegalArgumentException("username cannot be null or empty");
        }
        if(cred.getPassword() == null || cred.getPassword().isEmpty()) {
            throw new IllegalArgumentException("password cannot be null or empty");
        }
        try{
            UserDetails user = securityService.loadUserByUsername(cred.getUsername());
            if(encoder.matches(cred.getPassword(), user.getPassword())){
                return Base64.getEncoder().encodeToString(String.format("%s:%s", cred.getUsername(), cred.getPassword()).getBytes());
            }
            return null;
        }
        catch(UsernameNotFoundException ex){
            return null;
        }
    }
}
