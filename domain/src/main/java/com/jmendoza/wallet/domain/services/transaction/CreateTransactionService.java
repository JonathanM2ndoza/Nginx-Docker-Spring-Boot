package com.jmendoza.wallet.domain.services.transaction;

import com.jmendoza.wallet.common.constants.transaction.TransactionConstanst;
import com.jmendoza.wallet.common.customannotations.UseCase;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ParameterNotFoundException;
import com.jmendoza.wallet.domain.model.transaction.Transaction;
import com.jmendoza.wallet.domain.ports.inbound.transaction.CreateTransactionUseCase;
import com.jmendoza.wallet.domain.ports.outbound.transaction.CreateTransactionPort;
import com.jmendoza.wallet.domain.services.customer.DeleteCustomerService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
@UseCase
public class CreateTransactionService implements CreateTransactionUseCase {

    private CreateTransactionPort createTransactionPort;
    private static final Logger loggerException = LogManager.getLogger(CreateTransactionService.class);

    @Override
    public void createTransaction(Transaction transaction) throws GlobalException, ParameterNotFoundException {

        try {
            if (StringUtils.isBlank(transaction.getAccountId()))
                getMessageParameterNotFoundException("accountId");
            if (StringUtils.isBlank(transaction.getTransactionTypeId()))
                getMessageParameterNotFoundException("transactionTypeId");
            if (transaction.getAmount() == null || transaction.getAmount().longValue() <= 0)
                getMessageParameterNotFoundException("amount");
            if (StringUtils.isBlank(transaction.getDescription()))
                getMessageParameterNotFoundException("description");

            createTransactionPort.createTransaction(transaction);

        } catch (Exception e) {
            loggerException.error(e);
            throw new GlobalException("createTransaction: " + e.getMessage());
        }
    }

    protected void getMessageParameterNotFoundException(String parameter) throws ParameterNotFoundException {
        throw new ParameterNotFoundException(TransactionConstanst.REQUIRED_PARAMETER + "\"" + parameter + "\"" + TransactionConstanst.IS_NOT_PRESENT);
    }
}
