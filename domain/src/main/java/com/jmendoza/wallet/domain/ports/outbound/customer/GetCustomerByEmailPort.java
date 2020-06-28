package com.jmendoza.wallet.domain.ports.outbound.customer;

import com.jmendoza.wallet.domain.model.customer.Customer;

public interface GetCustomerByEmailPort {
    Customer getCustomerByEmail(String email);
}

