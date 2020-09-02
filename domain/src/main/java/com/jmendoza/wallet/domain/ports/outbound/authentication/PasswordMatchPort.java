package com.jmendoza.wallet.domain.ports.outbound.authentication;

import com.jmendoza.wallet.common.exception.GlobalException;

public interface PasswordMatchPort {
    boolean passwordMatch(String email, String password) throws GlobalException;
}
