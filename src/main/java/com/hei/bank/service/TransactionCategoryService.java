package com.hei.bank.service;

import com.hei.bank.DAO.OverdraftInterestDAO;
import com.hei.bank.DAO.TransactionCategoryDAO;
import com.hei.bank.model.OverdraftInterest;
import com.hei.bank.model.TransactionCategory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionCategoryService implements EntityServices<TransactionCategory>{
    private TransactionCategoryDAO transactionCategoryDAO;

    public TransactionCategoryService(TransactionCategoryDAO transactionCategoryDAO) {
        this.transactionCategoryDAO = transactionCategoryDAO;
    }

    @Override
    public List<TransactionCategory> findAll() throws SQLException {
        return transactionCategoryDAO.findAll();
    }

    @Override
    public TransactionCategory findById(UUID id) throws SQLException {
        return transactionCategoryDAO.findById(id);
    }

    @Override
    public TransactionCategory save(TransactionCategory toSave) throws SQLException {
        return transactionCategoryDAO.save(toSave);
    }
}
