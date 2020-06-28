package com.jmendoza.wallet.domain.ports.outbound.customer;

public interface PasswordEncodePort {
    String passwordEncoder(String password);
}
