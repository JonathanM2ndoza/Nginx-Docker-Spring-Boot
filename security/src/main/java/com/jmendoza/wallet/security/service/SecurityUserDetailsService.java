package com.jmendoza.wallet.security.service;

import com.jmendoza.wallet.common.constants.customer.CustomerConstanst;
import com.jmendoza.wallet.common.exception.ResourceNotFoundException;
import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.outbound.customer.GetCustomerByEmailPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private GetCustomerByEmailPort getCustomerByEmailPort;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Customer customer;
        UserDetails userDetails = null;
        try {
            customer = getCustomerByEmailPort.getCustomerByEmail(email);
            if (customer == null)
                throw new ResourceNotFoundException(CustomerConstanst.CUSTOMER_NOT_FOUND + email);

            userDetails = new User(customer.getEmail(), customer.getPassword(), customer.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                    .collect(Collectors.toList()));

        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException(CustomerConstanst.CUSTOMER_NOT_FOUND + email, e);
        }
        return userDetails;
    }
}
