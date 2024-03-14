package com.hei.bank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class Account {
    private UUID id;
    private String lastName;
    private String firstName;
    private Timestamp dateOfBirth;
    private Double monthlyNetSalary;
    private String uniqueAccountNumber;
    private Boolean overdraftStatus;
}
