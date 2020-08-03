package com.jmendoza.wallet.domain.services.transaction;

import com.jmendoza.wallet.common.customannotations.UseCase;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.account.Account;
import com.jmendoza.wallet.domain.ports.inbound.transaction.GetTransactionsByAccountIdUseCase;
import com.jmendoza.wallet.domain.ports.outbound.transaction.GetTransactionsByAccountIdPort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
@UseCase
public class GetTransactionsByAccountIdService implements GetTransactionsByAccountIdUseCase {

    private GetTransactionsByAccountIdPort getTransactionsByAccountIdPort;
    private static final Logger loggerException = LogManager.getLogger(GetTransactionsByAccountIdService.class);

    @Override
    public Account getTransactionsByAccountId(String accountId) throws GlobalException {
        try {

            return getTransactionsByAccountIdPort.getTransactionsByAccountId(accountId);

        } catch (Exception e) {
            loggerException.error(e);
            throw new GlobalException("getTransactionsByAccountId: " + e.getMessage());
        }
    }
}
