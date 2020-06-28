package com.jmendoza.wallet.infrastructure.databases.postgresql.customer;

import com.jmendoza.wallet.domain.model.customer.Customer;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    @Autowired
    ModelMapper modelMapper;

    public CustomerJpaEntity mapToJpaEntity(Customer customer) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper.map(customer, CustomerJpaEntity.class);

    }

    public Customer mapToDomain(CustomerJpaEntity customerJpaEntity) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper.map(customerJpaEntity, Customer.class);
    }
}
