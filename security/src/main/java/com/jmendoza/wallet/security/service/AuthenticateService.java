package com.jmendoza.wallet.security.service;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public String createToken(String email, String password) throws GlobalException {

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtUtil.generateToken(userDetails);

        } catch (BadCredentialsException e) {
            throw new GlobalException("Incorrect username or password: " + e.getMessage());
        } catch (Exception e) {
            throw new GlobalException("createToken: " + e.getMessage());
        }
    }
}
