package com.jmendoza.wallet.infrastructure.databases.postgresql.customer;

import com.jmendoza.wallet.common.constants.authorization.AuthorizationConstanst;
import com.jmendoza.wallet.domain.model.authorization.Roles;
import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.infrastructure.databases.postgresql.authorization.RoleJpaEntity;
import com.jmendoza.wallet.infrastructure.databases.postgresql.authorization.RoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GetRoleCustomerAdapter {

    @Autowired
    private RoleJpaRepository roleJpaRepository;

    public void getRoleByName(Customer customer, Set<RoleJpaEntity> roleJpaEntities) {
        customer.getRoles().forEach(role1 -> {
            switch (role1.getRoleId()) {
                case "1":
                    RoleJpaEntity adminRole = roleJpaRepository.findByName(Roles.ROLE_ADMIN).orElseThrow(() -> new RuntimeException(AuthorizationConstanst.ERROR_ROLE_IS_NOT_FOUND));
                    roleJpaEntities.add(adminRole);
                    break;
                case "2":
                    RoleJpaEntity userRole = roleJpaRepository.findByName(Roles.ROLE_USER).orElseThrow(() -> new RuntimeException(AuthorizationConstanst.ERROR_ROLE_IS_NOT_FOUND));
                    roleJpaEntities.add(userRole);
                    break;
                default:
            }
        });
    }
}
