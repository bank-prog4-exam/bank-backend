package com.hei.bank.controller;

import com.hei.bank.model.Account;
import com.hei.bank.model.Transaction;
import com.hei.bank.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/all_accounts")
    public List<Account> findAll() throws SQLException{
        return accountService.findAll();
    }

    @GetMapping("/account/{id}")
    public Account findById(@PathVariable UUID id)throws SQLException{
        return accountService.findById(id);
    }

    @PostMapping("/new_account")
    public Account save(@RequestBody Account toSave) throws SQLException{
        return accountService.save(toSave);
    }

    @GetMapping("/account_balance/{id}/{timestamp}")
    public Map<String, Double> getAccountBalance(@PathVariable Timestamp timestamp, @PathVariable UUID id) throws SQLException{
        return accountService.getAccountBalance(timestamp,id);
    }
}
