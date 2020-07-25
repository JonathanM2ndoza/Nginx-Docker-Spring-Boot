package com.jmendoza.wallet.domain.model.transaction;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Transaction {
    private String transactionId;
    private String accountId;
    private String transactionTypeId;
    private Double amount;
    private String description;
    private LocalDateTime createdAt;
}
