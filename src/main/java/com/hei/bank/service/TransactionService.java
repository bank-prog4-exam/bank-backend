package com.hei.bank.service;

import com.hei.bank.DAO.TransactionDAO;
import com.hei.bank.model.Account;
import com.hei.bank.model.Transaction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
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

    public Account doTransaction(Transaction transaction) throws SQLException{
        return transactionDAO.doTransaction(transaction);
    }

    public List<String> getAllTransaction(UUID id) throws SQLException{
        return transactionDAO.getAllTransaction(id);
    }

    @Scheduled(fixedRate = 1000)
    public void autoDoTransaction() throws SQLException {
        List<Transaction> transactionList = transactionDAO.findAll();
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

        for (Transaction transaction : transactionList){
            LocalDateTime effectiveDate = transaction.getEffectiveDate().toLocalDateTime().withSecond(0).withNano(0); // Ignore seconds and nanoseconds

            if (effectiveDate.equals(now)) {
                doTransaction(transaction);
            }
        }
    }
}
