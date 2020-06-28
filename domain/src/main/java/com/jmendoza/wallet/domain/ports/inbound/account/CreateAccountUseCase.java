package com.jmendoza.wallet.domain.ports.inbound.account;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ParameterNotFoundException;
import com.jmendoza.wallet.domain.model.account.Account;

public interface CreateAccountUseCase {
    void createAccount(Account account) throws GlobalException, ParameterNotFoundException;
}
