package com.jmendoza.wallet.domain.ports.outbound.authentication;

public interface CreateTokenPort {
    String createToken(String email, String password);
}
