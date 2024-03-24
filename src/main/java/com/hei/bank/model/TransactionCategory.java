package com.hei.bank.model;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TransactionCategory {
    private int id;
    private String transactionCategoryName;
    private String description;
}
