package com.jmendoza.wallet.domain.ports.outbound.customer;

import com.jmendoza.wallet.domain.model.customer.Customer;

public interface DeleteCustomerPort {
    void deleteCustomer(Customer customer);
}
