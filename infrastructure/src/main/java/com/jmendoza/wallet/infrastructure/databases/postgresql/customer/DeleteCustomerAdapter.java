package com.jmendoza.wallet.infrastructure.databases.postgresql.customer;

import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.outbound.customer.DeleteCustomerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteCustomerAdapter implements DeleteCustomerPort {

    @Autowired
    private CustomerJpaRepository customerJpaRepository;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void deleteCustomer(Customer customer) {
        customerJpaRepository.delete(customerMapper.mapToJpaEntity(customer));
    }
}
