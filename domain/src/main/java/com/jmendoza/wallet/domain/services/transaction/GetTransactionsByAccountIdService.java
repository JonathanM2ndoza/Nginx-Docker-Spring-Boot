package com.jmendoza.wallet.domain.services.transaction;

import com.jmendoza.wallet.common.customannotations.UseCase;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.account.Account;
import com.jmendoza.wallet.domain.ports.inbound.transaction.GetTransactionsByAccountIdUseCase;
import com.jmendoza.wallet.domain.ports.outbound.transaction.GetTransactionsByAccountIdPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@UseCase
public class GetTransactionsByAccountIdService implements GetTransactionsByAccountIdUseCase {

    private GetTransactionsByAccountIdPort getTransactionsByAccountIdPort;

    @Override
    public Account getTransactionsByAccountId(String accountId) throws GlobalException {
        try {

            return getTransactionsByAccountIdPort.getTransactionsByAccountId(accountId);

        } catch (Exception e) {
            throw new GlobalException("getTransactionsByAccountId: " + e.getMessage());
        }
    }
}
