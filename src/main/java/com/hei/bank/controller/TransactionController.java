package com.hei.bank.controller;

import com.hei.bank.model.Account;
import com.hei.bank.model.Transaction;
import com.hei.bank.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @GetMapping("/all_transactions")
    public List<Transaction> findAll() throws SQLException {
        return transactionService.findAll();
    }

    @GetMapping("/transaction/{id}")
    public Transaction findById(@PathVariable UUID id)throws SQLException{
        return transactionService.findById(id);
    }

    @PostMapping("/new_transaction")
    public Transaction save(@RequestBody Transaction toSave) throws SQLException{
        return transactionService.save(toSave);
    }

    @PutMapping("/do_transaction")
    public Account doTransaction(@RequestBody Transaction transaction) throws SQLException {
        return transactionService.doTransaction(transaction);
    }
    @GetMapping("/all_transactions/{id}")
    public List<String> getAllTransaction(@PathVariable UUID id) throws SQLException{
        return transactionService.getAllTransaction(id);
    }
}
