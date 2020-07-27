package com.jmendoza.wallet.application.rest.request.authentication;

import com.jmendoza.wallet.domain.model.authorization.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Role> roles;
}
