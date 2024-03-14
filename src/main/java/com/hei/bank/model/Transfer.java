package com.hei.bank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Transfer {
    private UUID id;
    private UUID idSenderAccount;
    private UUID idReceiverAccount;
    private Double transferAmount;
    private String reason;
    private Timestamp effectiveDate;
    private Timestamp registrationDate;
    private String status;
}
