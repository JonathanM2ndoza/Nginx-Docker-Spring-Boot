package com.jmendoza.wallet.infrastructure.databases.postgresql.customer;

import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.outbound.customer.UpdateCustomerPort;
import com.jmendoza.wallet.infrastructure.databases.postgresql.authorization.RoleJpaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UpdateCustomerAdapter implements UpdateCustomerPort {

    @Autowired
    private CustomerJpaRepository customerJpaRepository;
    @Autowired
    private GetRoleCustomerAdapter getRoleCustomerAdapter;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void updateCustomer(Customer customer) {
        Set<RoleJpaEntity> roleJpaEntities = new HashSet<>();
        getRoleCustomerAdapter.getRoleByName(customer, roleJpaEntities);
        final CustomerJpaEntity customerJpaEntity = customerMapper.mapToJpaEntity(customer);
        customerJpaEntity.setRoleJpaEntities(roleJpaEntities);
        customerJpaRepository.save(customerJpaEntity);
    }
}
