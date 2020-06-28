package com.jmendoza.wallet.domain.ports.outbound.customer;

import com.jmendoza.wallet.domain.model.customer.Customer;

public interface GetCustomerIdPort {
    Customer getCustomerById(String id);
}
