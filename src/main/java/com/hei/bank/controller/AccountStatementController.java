package com.hei.bank.controller;

import com.hei.bank.model.AccountStatement;
import com.hei.bank.service.AccountStatementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class AccountStatementController {
    private final AccountStatementService accountStatementService;


    public AccountStatementController(AccountStatementService accountStatementService) {
        this.accountStatementService = accountStatementService;
    }

    @GetMapping("/all_accountStatements")
    public List<AccountStatement> findAll() throws SQLException {
        return accountStatementService.findAll();
    }

    @GetMapping("/accountStatement/{id}")
    public AccountStatement findById(@PathVariable UUID id)throws SQLException{
        return accountStatementService.findById(id);
    }

    @PostMapping("/new_accountStatement")
    public AccountStatement save(@RequestBody AccountStatement toSave) throws SQLException{
        return accountStatementService.save(toSave);
    }
}
