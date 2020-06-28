package com.jmendoza.wallet.domain.ports.inbound.transaction;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ParameterNotFoundException;
import com.jmendoza.wallet.domain.model.transaction.Transaction;

public interface CreateTransactionUseCase {
    void createTransaction(Transaction transaction) throws GlobalException, ParameterNotFoundException;
}
