package com.jmendoza.wallet.domain.services.transaction;

import com.jmendoza.wallet.common.customannotations.UseCase;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.transaction.Transaction;
import com.jmendoza.wallet.domain.ports.inbound.transaction.GetTransactionUseCase;
import com.jmendoza.wallet.domain.ports.outbound.transaction.GetTransactionPort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
@UseCase
public class GetTransactionService implements GetTransactionUseCase {

    private GetTransactionPort getTransactionPort;
    private static final Logger loggerException = LogManager.getLogger(GetTransactionService.class);

    @Override
    public Transaction getTransaction(String transactionId) throws GlobalException {
        try {

            return getTransactionPort.getTransaction(transactionId);

        } catch (Exception e) {
            loggerException.error(e);
            throw new GlobalException("getTransaction: " + e.getMessage());
        }
    }
}
