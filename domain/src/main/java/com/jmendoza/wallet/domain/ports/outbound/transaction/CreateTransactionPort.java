package com.jmendoza.wallet.domain.ports.outbound.transaction;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.transaction.Transaction;

public interface CreateTransactionPort {
    void createTransaction(Transaction transaction) throws GlobalException;
}
