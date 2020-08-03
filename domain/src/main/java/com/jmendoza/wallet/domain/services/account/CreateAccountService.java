package com.jmendoza.wallet.domain.services.account;

import com.jmendoza.wallet.common.constants.account.AccountConstanst;
import com.jmendoza.wallet.common.customannotations.UseCase;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ParameterNotFoundException;
import com.jmendoza.wallet.domain.model.account.Account;
import com.jmendoza.wallet.domain.ports.inbound.account.CreateAccountUseCase;
import com.jmendoza.wallet.domain.ports.outbound.account.CreateAccountPort;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
@UseCase
public class CreateAccountService implements CreateAccountUseCase {

    private CreateAccountPort createAccountPort;
    private static final Logger loggerException = LogManager.getLogger(CreateAccountService.class);

    @Override
    public void createAccount(Account account) throws GlobalException, ParameterNotFoundException {

        try {
            if (StringUtils.isBlank(account.getCustomerId()))
                getMessageParameterNotFoundException("customerId");
            if (StringUtils.isBlank(account.getAccountNumber()))
                getMessageParameterNotFoundException("accountNumber");
            if (account.getCurrentBalance() == null || account.getCurrentBalance().longValue() <= 0)
                getMessageParameterNotFoundException("currentBalance");

            createAccountPort.createAccount(account);

        } catch (Exception e) {
            loggerException.error(e);
            throw new GlobalException("createAccount: " + e.getMessage());
        }
    }

    private void getMessageParameterNotFoundException(String parameter) throws ParameterNotFoundException {
        throw new ParameterNotFoundException(AccountConstanst.REQUIRED_PARAMETER + "\"" + parameter + "\"" + AccountConstanst.IS_NOT_PRESENT);
    }
}
