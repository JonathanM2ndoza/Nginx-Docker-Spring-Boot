package com.jmendoza.wallet.application.rest.response.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jmendoza.wallet.domain.model.authorization.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInResponse {
    private String token;
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Role> roles;
    private Date createdAt;
    private Date updatedAt;
}
