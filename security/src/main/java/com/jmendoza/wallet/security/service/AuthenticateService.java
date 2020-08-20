package com.jmendoza.wallet.security.service;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    SecurityUserDetailsService securityUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private Environment env;

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
