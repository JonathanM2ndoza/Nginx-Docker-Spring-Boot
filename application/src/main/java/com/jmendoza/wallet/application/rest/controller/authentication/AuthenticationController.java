package com.jmendoza.wallet.application.rest.controller.authentication;

import com.jmendoza.wallet.application.rest.request.authentication.SignInRequest;
import com.jmendoza.wallet.application.rest.response.authentication.SignInResponse;
import com.jmendoza.wallet.application.rest.response.authentication.SignUpResponse;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ParameterNotFoundException;
import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.inbound.customer.CreateCustomerUseCase;
import com.jmendoza.wallet.domain.ports.outbound.customer.GetCustomerByEmailPort;
import com.jmendoza.wallet.security.service.AuthenticateService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private CreateCustomerUseCase createCustomerUseCase;
    private GetCustomerByEmailPort getCustomerByEmailPort;

    @Autowired
    private AuthenticateService authenticateService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody Customer customer) throws ParameterNotFoundException, GlobalException {
        createCustomerUseCase.createCustomer(customer);
        return ResponseEntity.ok().body(SignUpResponse.builder().customerId(customer.getCustomerId()).build());
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) throws GlobalException {

        String token = authenticateService.createToken(signInRequest.getEmail(), signInRequest.getPassword());
        Customer customer = getCustomerByEmailPort.getCustomerByEmail(signInRequest.getEmail());

        return ResponseEntity.ok().body(SignInResponse.builder().token(token)
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .roles(customer.getRoles())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build());
    }
}
