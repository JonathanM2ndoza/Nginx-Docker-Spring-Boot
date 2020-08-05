package com.jmendoza.wallet.domain.ports.inbound.customer;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.customer.Customer;

public interface GetCustomerByEmailUseCase {
    Customer getCustomerByEmail(String email) throws GlobalException;
}
