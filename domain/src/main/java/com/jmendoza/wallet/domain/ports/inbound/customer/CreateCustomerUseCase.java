package com.jmendoza.wallet.domain.ports.inbound.customer;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ParameterNotFoundException;
import com.jmendoza.wallet.domain.model.customer.Customer;

public interface CreateCustomerUseCase {
    void createCustomer(Customer customer) throws GlobalException, ParameterNotFoundException;
}
