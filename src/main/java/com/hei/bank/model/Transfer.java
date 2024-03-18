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
