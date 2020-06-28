package com.jmendoza.wallet.domain.ports.outbound.transaction;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.transaction.Transaction;

public interface GetTransactionPort {
    Transaction getTransaction(String transactionId) throws GlobalException;
}
