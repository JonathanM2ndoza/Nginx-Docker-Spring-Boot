package com.jmendoza.wallet.domain.ports.inbound.authentication;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.customer.Customer;

public interface SignInUseCase {
    Customer signIn(String email, String password) throws GlobalException;
}
