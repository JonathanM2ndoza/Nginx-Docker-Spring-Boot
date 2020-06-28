package com.jmendoza.wallet.domain.model.customer;

import com.jmendoza.wallet.domain.model.authorization.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Customer {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Role> roles;
    private Date createdAt;
    private Date updatedAt;
}
