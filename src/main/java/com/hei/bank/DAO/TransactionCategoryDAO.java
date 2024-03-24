package com.hei.bank.DAO;

import com.hei.bank.configuration.ConnectionDB;
import com.hei.bank.model.TransactionCategory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class TransactionCategoryDAO {
    public TransactionCategoryDAO() {
        ConnectionDB connectionDB = new ConnectionDB();
    }
    public List<TransactionCategory> findAll() throws SQLException {
        AutoCrudOperation<TransactionCategory> autoCrudOp = new AutoCrudOperation<>(TransactionCategory.class);
        return autoCrudOp.findAll();
    }

    public TransactionCategory findById(UUID id) throws SQLException {
        AutoCrudOperation<TransactionCategory> AutoCrudOperation = new AutoCrudOperation<>(TransactionCategory.class);
        return AutoCrudOperation.findById(id);
    }

    public TransactionCategory save(TransactionCategory transactionCategory) {
        AutoCrudOperation<TransactionCategory> AutoCrudOperation = new AutoCrudOperation<>(TransactionCategory.class);
        return AutoCrudOperation.save(transactionCategory);
    }
}
