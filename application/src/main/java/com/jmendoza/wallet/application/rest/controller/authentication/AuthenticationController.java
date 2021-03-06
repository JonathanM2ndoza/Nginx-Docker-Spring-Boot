package com.jmendoza.wallet.application.rest.controller.authentication;

import com.google.gson.JsonObject;
import com.jmendoza.wallet.application.rest.request.authentication.SignInRequest;
import com.jmendoza.wallet.application.rest.request.authentication.SignUpRequest;
import com.jmendoza.wallet.application.rest.request.authentication.TokenRefreshRequest;
import com.jmendoza.wallet.application.rest.response.authentication.SignInResponse;
import com.jmendoza.wallet.application.rest.response.authentication.SignUpResponse;
import com.jmendoza.wallet.application.rest.response.authentication.TokenRefreshResponse;
import com.jmendoza.wallet.common.constants.authorization.AuthorizationConstanst;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ParameterNotFoundException;
import com.jmendoza.wallet.common.log.LogContext;
import com.jmendoza.wallet.common.log.LogFacade;
import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.inbound.authentication.SignInUseCase;
import com.jmendoza.wallet.domain.ports.inbound.customer.CreateCustomerUseCase;
import com.jmendoza.wallet.security.service.AuthenticateService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private CreateCustomerUseCase createCustomerUseCase;
    private SignInUseCase signInUseCase;
    @Autowired
    private AuthenticateService authenticateService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Environment env;

    private static final LogFacade log = new LogFacade(AuthenticationController.class);

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) throws ParameterNotFoundException, GlobalException {
        Customer customer = modelMapper.map(signUpRequest, Customer.class);
        createCustomerUseCase.createCustomer(customer);
        return ResponseEntity.ok().body(SignUpResponse.builder().customerId(customer.getCustomerId()).build());
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest, HttpServletRequest request) throws GlobalException {
        LogContext logContext = new LogContext(request.getRemoteAddr(), "Sign In", AuthorizationConstanst.ANONYMOUS, "signIn");
        log.info(logContext, "Test Message");

        Customer customer = signInUseCase.signIn(signInRequest.getEmail(), signInRequest.getPassword());

        return ResponseEntity.ok().body(SignInResponse.builder().token(customer.getToken())
                .tokenExpiration(env.getProperty("security.jwt.expiration"))
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .roles(customer.getRoles())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build());
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<TokenRefreshResponse> tokenRefresh(@RequestBody TokenRefreshRequest tokenRefreshRequest) throws GlobalException {
        TokenRefreshResponse tokenRefreshResponse = TokenRefreshResponse.builder().build();
        tokenRefreshResponse.setToken(authenticateService.refreshToken(tokenRefreshRequest.getEmail(), tokenRefreshRequest.getEncryptedHash()));
        tokenRefreshResponse.setTokenExpiration(env.getProperty("security.jwt.expiration"));

        return ResponseEntity.ok().body(tokenRefreshResponse);
    }

    @GetMapping("/random_number")
    public ResponseEntity getRandomNumber() throws NoSuchAlgorithmException {
        Random rand = SecureRandom.getInstance("SHA1PRNG");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("randomNumber", String.format("%06d", rand.nextInt(999999)));

        return ResponseEntity.ok().body(jsonObject.toString());
    }
}
