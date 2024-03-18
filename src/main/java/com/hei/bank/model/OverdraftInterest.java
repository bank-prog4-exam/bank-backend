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
public class OverdraftInterest {
    private UUID id;
    private UUID idAccount;
    private Double InterestRateFirstDays;
    private Double InterestRateLastDays;
    private Timestamp modificationDate;
}
