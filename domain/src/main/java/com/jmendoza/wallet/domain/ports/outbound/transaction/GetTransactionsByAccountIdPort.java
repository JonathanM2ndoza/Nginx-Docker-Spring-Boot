package com.jmendoza.wallet.domain.ports.outbound.transaction;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.account.Account;

public interface GetTransactionsByAccountIdPort {
    Account getTransactionsByAccountId(String accountId) throws GlobalException;
}
