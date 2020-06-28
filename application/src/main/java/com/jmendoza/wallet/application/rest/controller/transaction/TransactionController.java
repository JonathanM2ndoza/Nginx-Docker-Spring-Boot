package com.jmendoza.wallet.application.rest.controller.transaction;

import com.jmendoza.wallet.application.rest.response.transaction.CreateTransactionResponse;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ParameterNotFoundException;
import com.jmendoza.wallet.domain.model.transaction.Transaction;
import com.jmendoza.wallet.domain.ports.inbound.transaction.CreateTransactionUseCase;
import com.jmendoza.wallet.domain.ports.inbound.transaction.GetTransactionUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;
    private final GetTransactionUseCase getTransactionUseCase;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CreateTransactionResponse> createTransaction(@RequestBody Transaction transaction) throws ParameterNotFoundException, GlobalException {
        createTransactionUseCase.createTransaction(transaction);
        return ResponseEntity.ok().body(CreateTransactionResponse.builder().transactionId(transaction.getTransactionId()).build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Transaction> getTransaction(@PathVariable(value = "id") String id) throws GlobalException {
        return ResponseEntity.ok().body(getTransactionUseCase.getTransaction(id));
    }
}
