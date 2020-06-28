package com.jmendoza.wallet.domain.ports.inbound.transaction;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.transaction.Transaction;

public interface GetTransactionUseCase {
    Transaction getTransaction(String transactionId) throws GlobalException;
}
