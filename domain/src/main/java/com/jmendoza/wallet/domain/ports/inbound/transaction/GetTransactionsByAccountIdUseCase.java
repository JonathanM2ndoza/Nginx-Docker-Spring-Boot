package com.jmendoza.wallet.domain.ports.inbound.transaction;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.account.Account;

public interface GetTransactionsByAccountIdUseCase {
    Account getTransactionsByAccountId(String accountId) throws GlobalException;
}
