package com.jmendoza.wallet.domain.ports.outbound.account;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.account.Account;

public interface GetAccountPort {
    Account getAccount(String accountId) throws GlobalException;
}
