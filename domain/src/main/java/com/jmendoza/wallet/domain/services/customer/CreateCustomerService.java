package com.jmendoza.wallet.domain.services.customer;

import com.jmendoza.wallet.common.constants.customer.CustomerConstanst;
import com.jmendoza.wallet.common.customannotations.UseCase;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ParameterNotFoundException;
import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.inbound.customer.CreateCustomerUseCase;
import com.jmendoza.wallet.domain.ports.outbound.customer.CreateCustomerPort;
import com.jmendoza.wallet.domain.ports.outbound.customer.ExistsCustomerPort;
import com.jmendoza.wallet.domain.ports.outbound.customer.PasswordEncodePort;
import com.jmendoza.wallet.domain.services.account.CreateAccountService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
@UseCase
public class CreateCustomerService implements CreateCustomerUseCase {

    private CreateCustomerPort createCustomerPort;
    private PasswordEncodePort passwordEncodePort;
    private ExistsCustomerPort existsCustomerPort;
    private static final Logger loggerException = LogManager.getLogger(CreateCustomerService.class);

    @Override
    public void createCustomer(Customer customer) throws GlobalException, ParameterNotFoundException {
        try {
            if (StringUtils.isBlank(customer.getFirstName()))
                getMessageParameterNotFoundException("firstName");
            if (StringUtils.isBlank(customer.getLastName()))
                getMessageParameterNotFoundException("lastName");
            if (StringUtils.isBlank(customer.getEmail()))
                getMessageParameterNotFoundException("email");
            if (StringUtils.isBlank(customer.getPassword()))
                getMessageParameterNotFoundException("password");
            if (customer.getRoles() == null)
                getMessageParameterNotFoundException("roles");

            if (existsCustomerPort.existsByEmail(customer.getEmail()))
                throw new GlobalException(CustomerConstanst.THIS_EMAIL_IS_ALREADY_REGISTERED);

            customer.setPassword(passwordEncodePort.passwordEncoder(customer.getPassword()));
            createCustomerPort.createCustomer(customer);
        } catch (Exception e) {
            loggerException.error(e);
            throw new GlobalException("createCustomer: " + e.getMessage());
        }
    }

    private void getMessageParameterNotFoundException(String parameter) throws ParameterNotFoundException {
        throw new ParameterNotFoundException(CustomerConstanst.REQUIRED_PARAMETER + "\"" + parameter + "\"" + CustomerConstanst.IS_NOT_PRESENT);
    }

}
