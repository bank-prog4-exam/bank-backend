package com.hei.bank.DAO;

import com.hei.bank.configuration.ConnectionDB;
import com.hei.bank.model.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class TransactionDAO {
    public TransactionDAO() {
        ConnectionDB connectionDB = new ConnectionDB();
    }
    public List<Transaction> findAll() throws SQLException {
        AutoCrudOperation<Transaction> autoCrudOp = new AutoCrudOperation<>(Transaction.class);
        return autoCrudOp.findAll();
    }

    public Transaction findById(UUID id) throws SQLException {
        AutoCrudOperation<Transaction> AutoCrudOperation = new AutoCrudOperation<>(Transaction.class);
        return AutoCrudOperation.findById(id);
    }

    public Transaction save(Transaction transaction) {
        AutoCrudOperation<Transaction> AutoCrudOperation = new AutoCrudOperation<>(Transaction.class);
        return AutoCrudOperation.save(transaction);
    }
}
