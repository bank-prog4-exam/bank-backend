package com.hei.bank.controller;

import com.hei.bank.model.OverdraftInterest;
import com.hei.bank.model.TransactionCategory;
import com.hei.bank.service.OverdraftInterestService;
import com.hei.bank.service.TransactionCategoryService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionCategoryController {
    private final TransactionCategoryService transactionCategoryService;

    public TransactionCategoryController(TransactionCategoryService transactionCategoryService) {
        this.transactionCategoryService = transactionCategoryService;
    }
    @GetMapping("/all_transaction_categories")
    public List<TransactionCategory> findAll() throws SQLException {
        return transactionCategoryService.findAll();
    }

    @GetMapping("/transaction_category/{id}")
    public TransactionCategory findById(@PathVariable UUID id)throws SQLException{
        return transactionCategoryService.findById(id);
    }

    @PostMapping("/new_transaction_category")
    public TransactionCategory save(@RequestBody TransactionCategory toSave) throws SQLException{
        return transactionCategoryService.save(toSave);
    }
}
