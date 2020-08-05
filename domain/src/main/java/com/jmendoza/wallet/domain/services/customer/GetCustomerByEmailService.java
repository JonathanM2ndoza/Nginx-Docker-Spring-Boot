package com.jmendoza.wallet.domain.services.customer;

import com.jmendoza.wallet.common.constants.customer.CustomerConstanst;
import com.jmendoza.wallet.common.customannotations.UseCase;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ResourceNotFoundException;
import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.inbound.customer.GetCustomerByEmailUseCase;
import com.jmendoza.wallet.domain.ports.outbound.customer.GetCustomerByEmailPort;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
@UseCase
public class GetCustomerByEmailService implements GetCustomerByEmailUseCase {

    private GetCustomerByEmailPort getCustomerByEmailPort;
    private static final Logger loggerException = LogManager.getLogger(GetCustomerByEmailService.class);

    @Override
    public Customer getCustomerByEmail(String email) throws GlobalException {
        try {
            if (StringUtils.isBlank(email))
                throw new ResourceNotFoundException(CustomerConstanst.CUSTOMER_NOT_FOUND + email);

            return getCustomerByEmailPort.getCustomerByEmail(email);
        } catch (Exception e) {
            loggerException.error(e);
            throw new GlobalException("getCustomerByEmail: " + e.getMessage());
        }
    }
}
