package com.hei.bank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AccountStatement {
    private UUID id;
    private UUID idAccount;
    private String operationMotive;
    private Double operationAmount;
    private Timestamp effective_date;
    private Double principalBalance;
}
