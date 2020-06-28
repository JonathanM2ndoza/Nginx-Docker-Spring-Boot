package com.jmendoza.wallet.domain.ports.inbound.customer;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ResourceNotFoundException;
import com.jmendoza.wallet.domain.model.customer.Customer;

public interface UpdateCustomerUseCase {
    void updateCustomer(String id, Customer customer) throws ResourceNotFoundException, GlobalException;
}
