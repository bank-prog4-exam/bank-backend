package com.hei.bank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class OverdraftInterest {
    private UUID id;
    private UUID idAccount;
    private Double InterestRateFirstDays;
    private Double InterestRateLastDays;
    private Timestamp modificationDate;
}
