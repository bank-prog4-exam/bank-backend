package com.hei.bank.DAO;

import com.hei.bank.configuration.ConnectionDB;
import com.hei.bank.model.AccountStatement;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class AccountStatementDAO {
    public AccountStatementDAO() {
        ConnectionDB connectionDB = new ConnectionDB();
    }
    public List<AccountStatement> findAll() throws SQLException {
        AutoCrudOperation<AccountStatement> autoCrudOp = new AutoCrudOperation<>(AccountStatement.class);
        return autoCrudOp.findAll();
    }

    public AccountStatement findById(UUID id) throws SQLException {
        AutoCrudOperation<AccountStatement> AutoCrudOperation = new AutoCrudOperation<>(AccountStatement.class);
        return AutoCrudOperation.findById(id);
    }

    public AccountStatement save(AccountStatement accountStatement) {
        AutoCrudOperation<AccountStatement> AutoCrudOperation = new AutoCrudOperation<>(AccountStatement.class);
        return AutoCrudOperation.save(accountStatement);
    }
}
