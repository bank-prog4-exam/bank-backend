package com.hei.bank.model;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
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
    private Timestamp statementDate;
    private String reference;
    private String operationMotive;
    private Double creditAmount;
    private Double debitAmount;
    private Double balance;
}
