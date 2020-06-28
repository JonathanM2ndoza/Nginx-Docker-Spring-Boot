package com.jmendoza.wallet.domain.services.account;

import com.jmendoza.wallet.common.customannotations.UseCase;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.account.Account;
import com.jmendoza.wallet.domain.ports.inbound.account.GetAccountUseCase;
import com.jmendoza.wallet.domain.ports.outbound.account.GetAccountPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@UseCase
public class GetAccountService implements GetAccountUseCase {

    private GetAccountPort getAccountPort;

    @Override
    public Account getAccount(String accountId) throws GlobalException {
        try {

            return getAccountPort.getAccount(accountId);

        } catch (Exception e) {
            throw new GlobalException("getAccount: " + e.getMessage());
        }
    }
}
