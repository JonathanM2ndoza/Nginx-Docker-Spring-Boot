package com.jmendoza.wallet.application.rest.controller.account;

import com.jmendoza.wallet.application.rest.response.account.CreateAccountResponse;
import com.jmendoza.wallet.common.constants.authorization.AuthorizationConstanst;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ParameterNotFoundException;
import com.jmendoza.wallet.common.log.LogContext;
import com.jmendoza.wallet.common.log.LogFacade;
import com.jmendoza.wallet.domain.model.account.Account;
import com.jmendoza.wallet.domain.ports.inbound.account.CreateAccountUseCase;
import com.jmendoza.wallet.domain.ports.inbound.account.GetAccountUseCase;
import com.jmendoza.wallet.domain.ports.inbound.transaction.GetTransactionsByAccountIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/accounts")
@AllArgsConstructor
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final GetTransactionsByAccountIdUseCase getTransactionsByAccountIdUseCase;

    private static final LogFacade log = new LogFacade(AccountController.class);

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody Account account, HttpServletRequest request) throws ParameterNotFoundException, GlobalException {
        LogContext logContext = new LogContext(request.getRemoteAddr(), "Create Account", String.valueOf(request.getAttribute(AuthorizationConstanst.USER)), "createAccount");
        logContext.setWildcard1("Test Wildcard1");
        logContext.setWildcard2("Test Wildcard2");
        logContext.setWildcard3("Test Wildcard3");
        log.info(logContext, "Test Message");

        createAccountUseCase.createAccount(account);

        return ResponseEntity.ok().body(CreateAccountResponse.builder().accountId(account.getAccountId()).build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Account> getAccount(@PathVariable(value = "id") String id) throws GlobalException {
        return ResponseEntity.ok().body(getAccountUseCase.getAccount(id));
    }

    @GetMapping("/{id}/transactions")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Account> getTransactionsByAccountId(@PathVariable(value = "id") String id) throws GlobalException {
        return ResponseEntity.ok().body(getTransactionsByAccountIdUseCase.getTransactionsByAccountId(id));
    }
}
