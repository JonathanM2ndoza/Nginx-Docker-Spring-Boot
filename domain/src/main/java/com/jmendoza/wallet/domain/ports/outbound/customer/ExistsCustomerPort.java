package com.jmendoza.wallet.domain.ports.outbound.customer;

public interface ExistsCustomerPort {
    boolean existsByEmail(String email);
}
