package com.jmendoza.wallet.domain.services.customer;

import com.jmendoza.wallet.common.constants.customer.CustomerConstanst;
import com.jmendoza.wallet.common.customannotations.UseCase;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ResourceNotFoundException;
import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.inbound.customer.UpdateCustomerUseCase;
import com.jmendoza.wallet.domain.ports.outbound.customer.GetCustomerIdPort;
import com.jmendoza.wallet.domain.ports.outbound.customer.PasswordEncodePort;
import com.jmendoza.wallet.domain.ports.outbound.customer.UpdateCustomerPort;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@UseCase
public class UpdateCustomerService implements UpdateCustomerUseCase {

    private GetCustomerIdPort getCustomerIdPort;
    private UpdateCustomerPort updateCustomerPort;
    private PasswordEncodePort passwordEncodePort;

    private ModelMapper modelMapper;

    @Override
    public void updateCustomer(String id, Customer customer) throws ResourceNotFoundException, GlobalException {
        try {
            Customer customer1 = getCustomerIdPort.getCustomerById(id);
            if (customer1 == null)
                throw new ResourceNotFoundException(CustomerConstanst.CUSTOMER_NOT_FOUND + id);

            // TODO: Eliminar, el Frontend debe enviar la clave cifrada.
            if (!StringUtils.isBlank(customer.getPassword()))
                customer.setPassword(passwordEncodePort.passwordEncoder(customer.getPassword()));

            customer.setCustomerId(id);
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            modelMapper.map(customer, customer1);
            updateCustomerPort.updateCustomer(customer1);
        } catch (Exception e) {
            throw new GlobalException("updateCustomer: " + e.getMessage());
        }
    }
}
