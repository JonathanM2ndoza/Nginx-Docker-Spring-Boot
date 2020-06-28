package com.jmendoza.wallet.domain.services.transaction;

import com.jmendoza.wallet.common.customannotations.UseCase;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.transaction.Transaction;
import com.jmendoza.wallet.domain.ports.inbound.transaction.GetTransactionUseCase;
import com.jmendoza.wallet.domain.ports.outbound.transaction.GetTransactionPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@UseCase
public class GetTransactionService implements GetTransactionUseCase {

    private GetTransactionPort getTransactionPort;

    @Override
    public Transaction getTransaction(String transactionId) throws GlobalException {
        try {

            return getTransactionPort.getTransaction(transactionId);

        } catch (Exception e) {
            throw new GlobalException("getTransaction: " + e.getMessage());
        }
    }
}
