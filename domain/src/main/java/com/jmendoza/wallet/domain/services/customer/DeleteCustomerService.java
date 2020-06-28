package com.jmendoza.wallet.domain.services.customer;

import com.jmendoza.wallet.common.constants.customer.CustomerConstanst;
import com.jmendoza.wallet.common.customannotations.UseCase;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ResourceNotFoundException;
import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.inbound.customer.DeleteCustomerUseCase;
import com.jmendoza.wallet.domain.ports.outbound.customer.DeleteCustomerPort;
import com.jmendoza.wallet.domain.ports.outbound.customer.GetCustomerIdPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@UseCase
public class DeleteCustomerService implements DeleteCustomerUseCase {

    private GetCustomerIdPort getCustomerIdPort;
    private DeleteCustomerPort deleteCustomerPort;

    @Override
    public void deleteCustomer(String id) throws ResourceNotFoundException, GlobalException {
        try {
            Customer customer = getCustomerIdPort.getCustomerById(id);
            if (customer == null)
                throw new ResourceNotFoundException(CustomerConstanst.CUSTOMER_NOT_FOUND + id);

            deleteCustomerPort.deleteCustomer(customer);
        } catch (Exception e) {
            throw new GlobalException("deleteCustomer: " + e.getMessage());
        }
    }
}
