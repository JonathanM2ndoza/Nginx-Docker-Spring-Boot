package com.jmendoza.wallet.infrastructure.databases.postgresql.customer;

import com.jmendoza.wallet.common.constants.authorization.AuthorizationConstanst;
import com.jmendoza.wallet.domain.model.authorization.Roles;
import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.outbound.customer.*;
import com.jmendoza.wallet.infrastructure.databases.postgresql.authorization.RoleJpaEntity;
import com.jmendoza.wallet.infrastructure.databases.postgresql.authorization.RoleMapper;
import com.jmendoza.wallet.infrastructure.databases.postgresql.authorization.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class CustomerRepositoryAdapter implements CreateCustomerPort, ExistsCustomerPort, GetCustomerIdPort, DeleteCustomerPort, UpdateCustomerPort, GetCustomerByEmailPort {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void createCustomer(Customer customer) {
        Set<RoleJpaEntity> roleJpaEntities = new HashSet<>();
        CustomerJpaEntity customerJpaEntity = customerMapper.mapToJpaEntity(customer);
        getRoleByName(customer, roleJpaEntities);

        customerJpaEntity.setRoleJpaEntities(roleJpaEntities);
        customerRepository.save(customerJpaEntity);
        customer.setCustomerId(String.valueOf(customerJpaEntity.getId()));
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customerMapper.mapToJpaEntity(customer));
    }

    @Override
    public Customer getCustomerById(String id) {
        Customer customer = null;
        Optional<CustomerJpaEntity> customerJpaEntity = customerRepository.findById(Long.parseLong(id));

        if (customerJpaEntity.isPresent()) {
            final CustomerJpaEntity customerJpaEntity1 = customerJpaEntity.get();
            customer = customerMapper.mapToDomain(customerJpaEntity1);
            customer.setRoles(roleMapper.mapToDomain(customerJpaEntity1.getRoleJpaEntities()));
        }

        return customer;
    }

    @Override
    public void updateCustomer(Customer customer) {
        Set<RoleJpaEntity> roleJpaEntities = new HashSet<>();
        getRoleByName(customer, roleJpaEntities);
        final CustomerJpaEntity customerJpaEntity = customerMapper.mapToJpaEntity(customer);
        customerJpaEntity.setRoleJpaEntities(roleJpaEntities);
        customerRepository.save(customerJpaEntity);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        Customer customer = null;
        Optional<CustomerJpaEntity> customerJpaEntity = customerRepository.findByEmail(email);
        if (customerJpaEntity.isPresent()) {
            final CustomerJpaEntity customerJpaEntity1 = customerJpaEntity.get();
            customer = customerMapper.mapToDomain(customerJpaEntity1);
            customer.setRoles(roleMapper.mapToDomain(customerJpaEntity1.getRoleJpaEntities()));
        }
        return customer;
    }

    private void getRoleByName(Customer customer, Set<RoleJpaEntity> roleJpaEntities) {
        customer.getRoles().forEach(role1 -> {
            switch (role1.getRoleId()) {
                case "1":
                    RoleJpaEntity adminRole = roleRepository.findByName(Roles.ROLE_ADMIN).orElseThrow(() -> new RuntimeException(AuthorizationConstanst.ERROR_ROLE_IS_NOT_FOUND));
                    roleJpaEntities.add(adminRole);
                    break;
                case "2":
                    RoleJpaEntity userRole = roleRepository.findByName(Roles.ROLE_USER).orElseThrow(() -> new RuntimeException(AuthorizationConstanst.ERROR_ROLE_IS_NOT_FOUND));
                    roleJpaEntities.add(userRole);
                    break;
                default:
                    // TODO: Role by default
            }
        });
    }
}
