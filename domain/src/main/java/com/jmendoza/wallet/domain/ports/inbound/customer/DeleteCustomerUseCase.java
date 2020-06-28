package com.jmendoza.wallet.domain.ports.inbound.customer;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ResourceNotFoundException;

public interface DeleteCustomerUseCase {
    void deleteCustomer(String id) throws ResourceNotFoundException, GlobalException;
}
