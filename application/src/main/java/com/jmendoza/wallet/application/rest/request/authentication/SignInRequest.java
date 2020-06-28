package com.jmendoza.wallet.application.rest.request.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInRequest {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
