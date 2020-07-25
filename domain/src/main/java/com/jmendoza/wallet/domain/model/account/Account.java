package com.jmendoza.wallet.domain.model.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jmendoza.wallet.domain.model.transaction.Transaction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {
    private String accountId;
    private String customerId;
    private String accountNumber;
    private Double currentBalance;
    private LocalDateTime createdAt;
    private List<Transaction> transactionList;
}
