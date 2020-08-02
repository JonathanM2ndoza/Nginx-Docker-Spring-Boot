package com.jmendoza.wallet.infrastructure.databases.postgresql.customer;

import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.outbound.customer.CreateCustomerPort;
import com.jmendoza.wallet.infrastructure.databases.postgresql.authorization.RoleJpaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CreateCustomerAdapter implements CreateCustomerPort {
    @Autowired
    private CustomerJpaRepository customerJpaRepository;
    @Autowired
    private GetRoleCustomerAdapter getRoleCustomerAdapter;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void createCustomer(Customer customer) {
        Set<RoleJpaEntity> roleJpaEntities = new HashSet<>();
        CustomerJpaEntity customerJpaEntity = customerMapper.mapToJpaEntity(customer);
        getRoleCustomerAdapter.getRoleByName(customer, roleJpaEntities);

        customerJpaEntity.setRoleJpaEntities(roleJpaEntities);
        customerJpaRepository.save(customerJpaEntity);
        customer.setCustomerId(String.valueOf(customerJpaEntity.getId()));
    }
}
