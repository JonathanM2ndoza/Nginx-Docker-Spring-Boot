package com.jmendoza.wallet.security.util;

import com.jmendoza.wallet.domain.ports.outbound.customer.PasswordEncodePort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptAdapter implements PasswordEncodePort {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public String passwordEncoder(String password) {
        return bCryptPasswordEncoder.encode(password);

    }
}

