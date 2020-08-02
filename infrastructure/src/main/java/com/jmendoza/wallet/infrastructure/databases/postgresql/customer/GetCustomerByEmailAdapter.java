package com.jmendoza.wallet.infrastructure.databases.postgresql.customer;

import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.outbound.customer.GetCustomerByEmailPort;
import com.jmendoza.wallet.infrastructure.databases.postgresql.authorization.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetCustomerByEmailAdapter implements GetCustomerByEmailPort {

    @Autowired
    private CustomerJpaRepository customerJpaRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Customer getCustomerByEmail(String email) {
        Customer customer = null;
        Optional<CustomerJpaEntity> customerJpaEntity = customerJpaRepository.findByEmail(email);
        if (customerJpaEntity.isPresent()) {
            final CustomerJpaEntity customerJpaEntity1 = customerJpaEntity.get();
            customer = customerMapper.mapToDomain(customerJpaEntity1);
            customer.setRoles(roleMapper.mapToDomain(customerJpaEntity1.getRoleJpaEntities()));
        }
        return customer;
    }
}
