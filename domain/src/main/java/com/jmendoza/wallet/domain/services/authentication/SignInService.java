package com.jmendoza.wallet.domain.services.authentication;

import com.jmendoza.wallet.common.customannotations.UseCase;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.inbound.authentication.SignInUseCase;
import com.jmendoza.wallet.domain.ports.outbound.authentication.CreateTokenPort;
import com.jmendoza.wallet.domain.ports.outbound.authentication.PasswordMatchPort;
import com.jmendoza.wallet.domain.ports.outbound.customer.GetCustomerByEmailPort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
@UseCase
public class SignInService implements SignInUseCase {

    private GetCustomerByEmailPort getCustomerByEmailPort;
    private PasswordMatchPort passwordMatchPort;
    private CreateTokenPort createTokenPort;

    private static final Logger loggerException = LogManager.getLogger(SignInService.class);

    @Override
    public Customer signIn(String email, String password) throws GlobalException {
        Customer customer = null;
        try {
            if (passwordMatchPort.passwordMatch(email, password)) {
                customer = getCustomerByEmailPort.getCustomerByEmail(email);
                customer.setToken(createTokenPort.createToken(email, password));
            } else
                throw new GlobalException("Incorrect username or password");

        } catch (Exception e) {
            loggerException.error(e);
            throw new GlobalException("signIn: " + e);
        }
        return customer;
    }
}
