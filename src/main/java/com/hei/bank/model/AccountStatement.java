package com.hei.bank.model;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AccountStatement {
    private UUID id;
    private UUID idAccount;
    private String operationMotive;
    private Double operationAmount;
    private String operationType;
    private Timestamp effective_date;
    private Double principalBalance;
}
