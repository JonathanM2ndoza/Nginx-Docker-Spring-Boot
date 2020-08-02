package com.jmendoza.wallet.infrastructure.databases.postgresql.customer;

import com.jmendoza.wallet.domain.ports.outbound.customer.ExistsCustomerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistsCustomerAdapter implements ExistsCustomerPort {

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @Override
    public boolean existsByEmail(String email) {
        return customerJpaRepository.existsByEmail(email);
    }
}
