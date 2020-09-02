package com.jmendoza.wallet.infrastructure.security.authentication;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.ports.outbound.authentication.PasswordMatchPort;
import com.jmendoza.wallet.security.service.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordMatchAdapter implements PasswordMatchPort {

    @Autowired
    private AuthenticateService authenticateService;

    @Override
    public boolean passwordMatch(String email, String password) throws GlobalException {
        return authenticateService.passwordMatch(email, password);
    }
}
