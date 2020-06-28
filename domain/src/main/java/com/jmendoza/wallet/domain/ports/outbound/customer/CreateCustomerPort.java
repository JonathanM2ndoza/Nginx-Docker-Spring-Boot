package com.jmendoza.wallet.domain.ports.outbound.customer;

import com.jmendoza.wallet.domain.model.customer.Customer;

public interface CreateCustomerPort {
    void createCustomer(Customer customer);
}
