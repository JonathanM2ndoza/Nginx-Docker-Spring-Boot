package com.jmendoza.wallet.infrastructure.security.authentication;

import com.jmendoza.wallet.domain.ports.outbound.authentication.CreateTokenPort;
import com.jmendoza.wallet.security.service.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateTokenAdapter implements CreateTokenPort {

    @Autowired
    private AuthenticateService authenticateService;

    @Override
    public String createToken(String email, String password) {
        return authenticateService.createToken(email, password);
    }
}
