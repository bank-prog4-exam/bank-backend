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
public class Account {
    private UUID id;
    private String lastName;
    private String firstName;
    private Timestamp dateOfBirth;
    private Double monthlyNetSalary;
    private String uniqueAccountNumber;
    private Boolean overdraftStatus;
    private Double principalBalance;
    private Timestamp lastOverdraftActivity;

}
