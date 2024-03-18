package com.hei.bank.service;

import com.hei.bank.DAO.TransactionDAO;
import com.hei.bank.model.Transaction;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService implements EntityServices<Transaction>{
    private TransactionDAO transactionDAO;

    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public List<Transaction> findAll() throws SQLException {
        return transactionDAO.findAll();
    }

    @Override
    public Transaction findById(UUID id) throws SQLException {
        return transactionDAO.findById(id);
    }

    @Override
    public Transaction save(Transaction toSave) throws SQLException {
        return transactionDAO.save(toSave);
    }
}
