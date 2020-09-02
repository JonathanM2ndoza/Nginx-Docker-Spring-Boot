package com.jmendoza.wallet.security.service;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthenticateService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    SecurityUserDetailsService securityUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private Environment env;

    public boolean passwordMatch(String email, String password) throws GlobalException {
        boolean passwordMatch = true;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            passwordMatch = false;
        } catch (Exception e) {
            throw new GlobalException("passwordMatch: " + e.getMessage());
        }
        return passwordMatch;
    }

    public String createToken(String email, String password) {
        UserDetails userDetails = new User(email, password, Collections.emptyList());
        return jwtUtil.generateToken(userDetails);
    }

    public String refreshToken(String email, String encryptedHash) throws GlobalException {
        String token = null;
        try {
            UserDetails userDetails = securityUserDetailsService.loadUserByUsername(email);
            if (userDetails != null && env.getRequiredProperty("security.jwt.refresh-token").equals(encryptedHash))
                token = jwtUtil.generateToken(userDetails);
            else
                throw new GlobalException("No match refreshToken");

        } catch (Exception e) {
            throw new GlobalException("tokenRefresh: " + e.getMessage());
        }
        return token;
    }
}
