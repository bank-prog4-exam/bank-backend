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
public class Transaction {
    private UUID id;
    private UUID idAccount;
    private Double transactionAmount;
    private String transactionType;
    private String reason;
    private Timestamp effectiveDate;
    private Timestamp registrationDate;
}
